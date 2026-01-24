package org.example.exceptions;

public class AccountWithIdNotFoundException extends RuntimeException{
    public AccountWithIdNotFoundException(String id) {
        super("User with ID " + id + " was not found.");
    }
}
