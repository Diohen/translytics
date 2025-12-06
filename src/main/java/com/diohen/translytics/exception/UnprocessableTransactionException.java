package com.diohen.translytics.exception;

public class UnprocessableTransactionException extends RuntimeException {
    public UnprocessableTransactionException(String message) {
        super(message);
    }
}
