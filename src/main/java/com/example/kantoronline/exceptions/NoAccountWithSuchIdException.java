package com.example.kantoronline.exceptions;

import lombok.Getter;

@Getter
public class NoAccountWithSuchIdException extends RuntimeException {

    private final String message;

    public NoAccountWithSuchIdException() {
        this.message = "Brak konta o podanym id";
    }
}
