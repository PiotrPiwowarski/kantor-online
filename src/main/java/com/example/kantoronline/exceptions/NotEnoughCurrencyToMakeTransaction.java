package com.example.kantoronline.exceptions;

import lombok.Getter;

@Getter
public class NotEnoughCurrencyToMakeTransaction extends RuntimeException {

    private final String message;

    public NotEnoughCurrencyToMakeTransaction() {
        this.message = "Brak wystarczającej ilości waluty do dokonania transakcji";
    }
}
