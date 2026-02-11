package org.example.exceptions;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String id) {
        super("Account with id " + id + " already exists.");
    }
}
