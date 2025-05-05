package com.expenses.expensetracker.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchEmailException extends RuntimeException
{
  public  NoSuchEmailException(String message)
  {
    super(message);
  }
}
