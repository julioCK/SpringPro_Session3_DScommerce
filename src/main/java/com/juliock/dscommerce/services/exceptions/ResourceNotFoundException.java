package com.juliock.dscommerce.services.exceptions;

/*
* Resource not Found: Status Code 404
* */

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
