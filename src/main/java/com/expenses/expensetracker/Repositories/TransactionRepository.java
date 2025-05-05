package com.expenses.expensetracker.Repositories;

import com.expenses.expensetracker.Exceptions.EtBadRequestException;
import com.expenses.expensetracker.Exceptions.EtResourceNotFoundException;
import com.expenses.expensetracker.domain.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepository implements TransactionRepositoryInterface
{
    @Override
    public List<Transaction> findAll(Integer userID, Integer categoryID) {
        return List.of();
    }

    @Override
    public Transaction findTransaction(Integer userID, Integer categoryID, Integer transactionID) throws EtResourceNotFoundException {
        return null;
    }

    @Override
    public Integer create(Integer userID, Integer categoryID, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        return 0;
    }

    @Override
    public void update(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction) throws EtBadRequestException {

    }

    @Override
    public void removeByID(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction) throws EtResourceNotFoundException {

    }
}
