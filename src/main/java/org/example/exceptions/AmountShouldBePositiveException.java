package org.example.exceptions;

public class AmountShouldBePositiveException extends RuntimeException {
    public AmountShouldBePositiveException(long amount) {
        super("Amount for the operation should be positive, provided amount: " + amount);
    }
}
