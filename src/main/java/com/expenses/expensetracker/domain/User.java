package com.expenses.expensetracker.domain;

public class User {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(Integer userId, String firstName, String lastName, String email, String password)
    {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
