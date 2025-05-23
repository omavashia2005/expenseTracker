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

import java.io.IOException;

public class AuthFilter extends GenericFilterBean
{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;


        String authHeader = httpRequest.getHeader("Authorization");

        if(authHeader != null)
        {
            String[] authHeaderArr = authHeader.split("Bearer ");

            if(authHeaderArr.length > 1 && authHeaderArr[1] != null)
            {
                String token = authHeaderArr[1];
                try{
                    Claims claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY)
                            .parseClaimsJws(token).getBody();
                    httpRequest.setAttribute("userId", Integer.parseInt(claims.get("userID").toString()));
                }catch (Exception e)
                {
                    httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Invalid token");
                    return;
                }
            }
            else{
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Auth token must be Bearer [token]");
                return;
            }
        } else{
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization header missing");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}