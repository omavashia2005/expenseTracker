package com.expenses.expensetracker.services;

import com.expenses.expensetracker.Exceptions.EtBadRequestException;
import com.expenses.expensetracker.Exceptions.EtResourceNotFoundException;
import com.expenses.expensetracker.Repositories.TransactionRepository;
import com.expenses.expensetracker.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionService implements TransactionServiceInterface
{
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> fetchAllTransactions(Integer userID, Integer categoryID) {
        return transactionRepository.findAll(userID, categoryID);
    }

    @Override
    public Transaction fetchTransactionByID(Integer userID, Integer categoryID, Integer transactionID) throws EtResourceNotFoundException {
        return transactionRepository.findTransaction(userID, categoryID, transactionID);
    }

    @Override
    public Transaction addTransactionByID(Integer userID, Integer categoryID, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        int transactionID = transactionRepository.create(userID, categoryID, amount, note, transactionDate);
        return transactionRepository.findTransaction(userID, categoryID, transactionID);

    }

    @Override
    public void updateTransaction(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction) throws EtBadRequestException {
        transactionRepository.update(userID, categoryID, transactionID, transaction);
    }

    @Override
    public void removeTransaction(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction) throws EtResourceNotFoundException {
        transactionRepository.removeByID(userID, categoryID, transactionID, transaction);
    }
}
