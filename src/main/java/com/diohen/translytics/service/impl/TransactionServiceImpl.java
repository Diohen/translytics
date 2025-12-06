package com.diohen.translytics.service.impl;

import com.diohen.translytics.model.Transaction;
import com.diohen.translytics.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final List<Transaction> transactions = new CopyOnWriteArrayList<>();

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        log.info("New transaction received, amount {} is due on date {}.",
                transaction.value(),
                transaction.transactionDateTime());
    }
}
