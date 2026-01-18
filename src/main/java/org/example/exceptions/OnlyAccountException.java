package org.example.exceptions;

public class OnlyAccountException extends RuntimeException {
    public OnlyAccountException(String id) {
        super("Can't delete account the only one user's account " + id);
    }
}
