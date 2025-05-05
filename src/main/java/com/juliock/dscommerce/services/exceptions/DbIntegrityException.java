package com.juliock.dscommerce.services.exceptions;

public class DbIntegrityException extends RuntimeException {
    public DbIntegrityException(String message) {
        super(message);
    }
}
