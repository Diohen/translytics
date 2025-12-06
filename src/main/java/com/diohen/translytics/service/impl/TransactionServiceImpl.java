package com.diohen.translytics.service.impl;

import com.diohen.translytics.exception.UnprocessableTransactionException;
import com.diohen.translytics.model.Transaction;
import com.diohen.translytics.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final List<Transaction> transactions = new CopyOnWriteArrayList<>();

    @Override
    public void addTransaction(Transaction transaction) {
            validateTransaction(transaction);
            transactions.add(transaction);

            log.info("New transaction received, amount {} is due on date {}.",
                    transaction.value(),
                    transaction.transactionDateTime());
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction.value().compareTo(BigDecimal.ZERO) < 0) {
            log.error("Transaction amount {} is negative.", transaction.value());
            throw new UnprocessableTransactionException("Negative transaction are not allowed.");
        }

        if (transaction.transactionDateTime().isAfter(OffsetDateTime.now(ZoneId.of("America/Sao_Paulo")))) {
            log.error("Transaction date-time {} is in the future.",
                    transaction.transactionDateTime());
            throw new UnprocessableTransactionException("Transaction date-time cannot be in the future.");
        }
    }

}
