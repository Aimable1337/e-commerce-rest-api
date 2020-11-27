package com.spring.rest.ecommerce.exception;

public class UsernameAlreadyExistException extends RuntimeException {
    public UsernameAlreadyExistException(String message, Throwable cause){
        super(message, cause);
    }

    public UsernameAlreadyExistException(String message){
        super(message);
    }

    public UsernameAlreadyExistException(Throwable cause){
        super(cause);
    }
}
