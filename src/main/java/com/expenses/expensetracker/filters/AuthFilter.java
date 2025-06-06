package com.expenses.expensetracker.filters;

import com.expenses.expensetracker.services.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;
import jakarta.servlet.http.Cookie;

import java.io.IOException;

public class AuthFilter extends GenericFilterBean
{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String token = null;

        if(httpRequest.getCookies() != null)
        {
            for (jakarta.servlet.http.Cookie cookie : httpRequest.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "JWT cookie is missing");
            return;
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Constants.API_SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            httpRequest.setAttribute("userId", Integer.parseInt(claims.get("userID").toString()));
        } catch (Exception e) {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Invalid token");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}