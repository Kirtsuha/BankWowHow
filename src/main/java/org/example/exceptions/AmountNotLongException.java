package org.example.exceptions;

public class AmountNotLongException extends RuntimeException {
    public AmountNotLongException() {
        super("Amount should be a positive number");
    }
}
