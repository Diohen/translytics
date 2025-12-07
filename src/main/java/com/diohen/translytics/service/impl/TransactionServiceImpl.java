package com.diohen.translytics.service.impl;

import com.diohen.translytics.exception.UnprocessableTransactionException;
import com.diohen.translytics.model.Transaction;
import com.diohen.translytics.model.dto.GetStatisticsDTO;
import com.diohen.translytics.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Override
    public void clearTransactions() {
        log.info("Total {} transactions.", transactions.size());
        transactions.clear();
        log.info("All transactions were clear.");
    }

    @Override
    public GetStatisticsDTO getStatisticsForLastMinutes(long minutes) {

        OffsetDateTime now = OffsetDateTime.now(ZoneId.of("America/Sao_Paulo"));
        OffsetDateTime limitPeriod = now.minusMinutes(minutes);

        List<Transaction> filtered = transactions.stream()
                .filter(t -> !t.transactionDateTime().isAfter(limitPeriod))
                .toList();

        if (filtered.isEmpty()) {
            return new GetStatisticsDTO(0,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO);
        }

        int count = filtered.size();

        BigDecimal sum = filtered.stream()
                .map(Transaction::value)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal min = filtered.stream()
                .map(Transaction::value)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal max = filtered.stream()
                .map(Transaction::value)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal avg = sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);

        return new GetStatisticsDTO(count, sum, avg, min, max);
    }
}
