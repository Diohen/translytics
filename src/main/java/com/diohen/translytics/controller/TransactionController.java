package com.diohen.translytics.controller;

import com.diohen.translytics.model.Transaction;
import com.diohen.translytics.model.dto.PostTransactionDTO;
import com.diohen.translytics.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public void receiveTransaction(@RequestBody PostTransactionDTO postTransaction) {
        transactionService.addTransaction(
                new Transaction(
                        postTransaction.value(),
                        postTransaction.transactionDateTime()
                )
        );
    }

    @DeleteMapping("/transactions")
    @ResponseStatus(HttpStatus.OK)
    public void clearTransactions() {
        transactionService.clearTransactions();
    }
}
