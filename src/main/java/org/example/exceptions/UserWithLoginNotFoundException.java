package org.example.exceptions;

public class UserWithLoginNotFoundException extends RuntimeException {
    public UserWithLoginNotFoundException(String login) {
        super("User with login " + login + " was not found.");
    }
}
