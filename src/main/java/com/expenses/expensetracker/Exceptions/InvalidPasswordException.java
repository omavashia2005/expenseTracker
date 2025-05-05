package com.expenses.expensetracker.Exceptions;

public class InvalidPasswordException extends RuntimeException
{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
