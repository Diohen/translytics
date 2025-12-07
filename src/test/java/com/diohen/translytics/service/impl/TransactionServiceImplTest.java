package com.diohen.translytics.service.impl;

import com.diohen.translytics.exception.UnprocessableTransactionException;
import com.diohen.translytics.model.Transaction;
import com.diohen.translytics.model.dto.GetStatisticsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceImplTest {

    private TransactionServiceImpl service;
    private final ZoneId zone = ZoneId.of("America/Sao_Paulo");

    @BeforeEach
    void setUp() {
        service = new TransactionServiceImpl();
    }

    @Test
    void addTransactionTest() {
        OffsetDateTime now = OffsetDateTime.now();
        Transaction tx = new Transaction(new BigDecimal("10.00"), now.minusSeconds(30));
        service.addTransaction(tx);
    }

    @Test
    void negativeValueTest() {
        OffsetDateTime now = OffsetDateTime.now();
        Transaction tx = new Transaction(new BigDecimal("-1.00"), now);
        assertThrows(UnprocessableTransactionException.class, () -> service.addTransaction(tx));
    }

    @Test
    void futureDateTest() {
        OffsetDateTime now = OffsetDateTime.now();
        Transaction tx = new Transaction(new BigDecimal("5.00"), now.plusMinutes(5));
        assertThrows(UnprocessableTransactionException.class, () -> service.addTransaction(tx));
    }

    @Test
    void clearTransactionsTest() {
        service.clearTransactions();
    }

    @Test
    void emptyTransactionsTest() {
        service.getStatisticsForLastMinutes(5);
    }

    @Test
    void getStatisticsTest() {
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of("America/Sao_Paulo"));
        Transaction tx = new Transaction(new BigDecimal("10.00"), now.minusSeconds(60));
        service.addTransaction(tx);
        service.getStatisticsForLastMinutes(5);
    }
}