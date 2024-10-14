package com.example.kantoronline.exceptions;


import lombok.Getter;

@Getter
public class AccountWithSuchEmailAlreadyExistsException extends RuntimeException {

    private final String message;

    public AccountWithSuchEmailAlreadyExistsException() {
        this.message = "Użytkownik o podanym emailu już istnieje";
    }
}
