package com.diohen.translytics.service;

import com.diohen.translytics.model.Transaction;
import com.diohen.translytics.model.dto.PostTransactionDTO;

public interface TransactionService {
    void addTransaction(Transaction transaction);
}
