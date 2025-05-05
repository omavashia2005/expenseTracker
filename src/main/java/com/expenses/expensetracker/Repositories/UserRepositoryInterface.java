package com.expenses.expensetracker.Repositories;

import com.expenses.expensetracker.Exceptions.EtAuthException;
import com.expenses.expensetracker.Exceptions.InvalidPasswordException;
import com.expenses.expensetracker.Exceptions.NoSuchEmailException;
import com.expenses.expensetracker.domain.User;

import java.util.List;

public interface UserRepositoryInterface
{
    Integer create(String firstName, String lastName, String email, String password) throws EtAuthException;

    User findByEmailAndPassword(String email, String password) throws EtAuthException;

    Integer getCountByEmail(String email);

    String findByEmail(String email) throws NoSuchEmailException;

    User findById(Integer userID);

    void updatePassword(String email, String oldPassword, String newPassword) throws InvalidPasswordException;

}
