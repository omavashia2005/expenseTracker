package com.expenses.expensetracker.resources;

import com.expenses.expensetracker.Repositories.TransactionRepository;
import com.expenses.expensetracker.domain.Transaction;
import com.expenses.expensetracker.services.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/categories/{categoryID}/transactions")
public class transactionResource
{
    @Autowired
    TransactionService transactionService;

    @PostMapping("")
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
