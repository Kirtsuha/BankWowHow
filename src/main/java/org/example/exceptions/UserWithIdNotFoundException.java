package org.example.exceptions;

public class UserWithIdNotFoundException extends RuntimeException {
    public UserWithIdNotFoundException(String id) {
        super("User with ID " + id + " was not found.");
    }
}
