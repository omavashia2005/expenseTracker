package com.expenses.expensetracker.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EtBadCategoryRequestException extends RuntimeException {
    public EtBadCategoryRequestException(String message) {
        super(message);
    }
}
