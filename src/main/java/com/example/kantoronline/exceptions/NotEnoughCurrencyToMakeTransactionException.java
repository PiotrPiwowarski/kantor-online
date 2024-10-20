package com.example.kantoronline.exceptions;

import lombok.Getter;

@Getter
public class NotEnoughCurrencyToMakeTransactionException extends RuntimeException {

    private final String message;

    public NotEnoughCurrencyToMakeTransactionException() {
        this.message = "Brak wystarczającej ilości waluty do dokonania transakcji";
    }
}
