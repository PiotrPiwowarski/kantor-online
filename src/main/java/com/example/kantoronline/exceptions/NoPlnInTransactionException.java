package com.example.kantoronline.exceptions;

import lombok.Getter;

@Getter
public class NoPlnInTransactionException extends RuntimeException {

    private final String message;

    public NoPlnInTransactionException() {
        this.message = "PLN musi być jedną z walut transakcji";
    }
}
