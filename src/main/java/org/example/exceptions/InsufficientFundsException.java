package org.example.exceptions;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String id) {
        super("There are insufficient funds on account " + id);
    }
}
