package com.expenses.expensetracker.services;

import com.expenses.expensetracker.Exceptions.EtAuthException;
import com.expenses.expensetracker.domain.User;

import java.util.List;

public interface UserServiceInterface
{
    User validateUser(String email, String password) throws EtAuthException;

    User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException;

    String findByEmail(String email);

    void updatePassword(String email, String oldPassword, String newPassword);
}
