package com.expenses.expensetracker.services;

import com.expenses.expensetracker.Exceptions.EtBadRequestException;
import com.expenses.expensetracker.Exceptions.EtResourceNotFoundException;
import com.expenses.expensetracker.domain.Transaction;

import java.util.List;

public interface TransactionServiceInterface
{
    List<Transaction> fetchAllTransactions(Integer userID, Integer categoryID);
    Transaction fetchTransactionByID(Integer userID, Integer categoryID, Integer transactionID) throws EtResourceNotFoundException;
    Transaction addTransactionByID(Integer userID, Integer categoryID, Double amount, String note, Long transactionDate) throws EtBadRequestException;
    void updateTransaction(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction) throws EtBadRequestException;
    void removeTransaction(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction) throws EtResourceNotFoundException;
}
