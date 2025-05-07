package com.expenses.expensetracker.Repositories;

import com.expenses.expensetracker.Exceptions.EtBadRequestException;
import com.expenses.expensetracker.Exceptions.EtResourceNotFoundException;
import com.expenses.expensetracker.domain.Transaction;

import java.util.List;

public interface TransactionRepositoryInterface
{
    List<Transaction> findAll(Integer userID);
    Transaction findTransaction(Integer userID, Integer categoryID ,Integer transactionID) throws EtResourceNotFoundException;
    Integer create(Integer userID, Integer categoryID, Double amount, String note, Long transactionDate) throws EtBadRequestException;
    void update(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction) throws EtBadRequestException;
    void removeByID(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction) throws EtResourceNotFoundException;

}
