package com.expenses.expensetracker.resources;

import com.expenses.expensetracker.domain.User;
import com.expenses.expensetracker.services.CategoryService;
import com.expenses.expensetracker.services.Constants;

import com.expenses.expensetracker.services.UserServiceInterface;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class userResource
{

    @Autowired
    UserServiceInterface userService;
    @Autowired
    private CategoryService categoryService;


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap)
    {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        User user = userService.validateUser(email, password);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap)
    {
        String firstName = (String) userMap.get("firstName");
        String lastName = (String) userMap.get("lastName");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        User user = userService.registerUser(firstName, lastName, email, password);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @GetMapping("/findByEmail")
    public String findByEmail(@RequestBody String email)
    {
        return userService.findByEmail(email);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody Map<String, Object> userCredentials)
    {
        String email = (String) userCredentials.get("email");
        String oldPassword = (String) userCredentials.get("oldPassword");
        String newPassword = (String) userCredentials.get("newPassword");

        userService.updatePassword(email, oldPassword, newPassword);
        String message = "Password updated successfully";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private Map<String, String> generateJWTToken(User user){
        long timeTamp = System.currentTimeMillis();

        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timeTamp))
                .setExpiration(new Date(timeTamp + Constants.TOKEN_VALIDITY))
                .claim("userID", user.getUserId())
                .claim("Email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();

        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        return map;
    }
}
