package com.diohen.translytics.controller;

import com.diohen.translytics.model.Transaction;
import com.diohen.translytics.model.dto.PostTransactionDTO;
import com.diohen.translytics.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void receiveTransaction(@RequestBody PostTransactionDTO postTransaction) {
        transactionService.addTransaction(
                new Transaction(
                        postTransaction.value(),
                        postTransaction.transactionDateTime()
                )
        );
    }
}
