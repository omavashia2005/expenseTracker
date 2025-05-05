package com.expenses.expensetracker.services;

import com.expenses.expensetracker.Exceptions.EtBadCategoryRequestException;
import com.expenses.expensetracker.Exceptions.EtCategoryNotFoundException;
import com.expenses.expensetracker.domain.Transaction;

import java.util.List;

public interface TransactionServiceInterface
{
    List<Transaction> fetchAllTransactions(Integer userID, Integer categoryID);
    Transaction fetchTransactionByID(Integer userID, Integer categoryID, Integer transactionID) throws EtCategoryNotFoundException;
    Transaction addTransactionByID(Integer userID, Integer categoryID, Double amount, String note, Long transactionDate) throws EtBadCategoryRequestException;
    void updateTransaction(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction) throws EtBadCategoryRequestException;
    void removeTransaction(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction) throws EtCategoryNotFoundException;

}
