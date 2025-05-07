package com.expenses.expensetracker.resources;

import com.expenses.expensetracker.Repositories.TransactionRepository;
import com.expenses.expensetracker.domain.Transaction;
import com.expenses.expensetracker.services.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class transactionResource
{
    @Autowired
    TransactionService transactionService;

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> findAllTransactions(HttpServletRequest request)

    {
        int userID = (Integer) request.getAttribute("userId");
        List<Transaction> allTransactions = transactionService.fetchAllTransactions(userID);
        return new ResponseEntity<>(allTransactions, HttpStatus.FOUND);
    }


    @GetMapping("/{categoryID}/transactions/{transactionID}")
    public ResponseEntity<Transaction> findTransactionByID(HttpServletRequest request,
                                                           @PathVariable("categoryID") Integer categoryID,
                                                           @PathVariable("transactionID") Integer transactionID)
    {
        int userID = (Integer) request.getAttribute("userId");
        Transaction transaction = transactionService.fetchTransactionByID(userID, categoryID, transactionID);

        return new ResponseEntity<>(transaction, HttpStatus.FOUND);
    }

    @PostMapping("{categoryID}/transactions")
    public ResponseEntity<Transaction> addTransaction(HttpServletRequest request, @PathVariable Integer categoryID,
                                                      @RequestBody Map<String, Object> transactionMap){
        int userID = (Integer) request.getAttribute("userId");
        Double amount = Double.valueOf(transactionMap.get("Amount").toString());
        String note = (String) transactionMap.get("note");
        Long transactionDate = (Long) transactionMap.get("transactionDate");
        Transaction transaction = transactionService.addTransactionByID(userID, categoryID, amount, note, transactionDate);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

}
