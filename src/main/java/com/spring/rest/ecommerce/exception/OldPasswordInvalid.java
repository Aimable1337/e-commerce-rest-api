package com.spring.rest.ecommerce.exception;

public class OldPasswordInvalid extends RuntimeException{
    public OldPasswordInvalid(String message, Throwable cause){
        super(message, cause);
    }

    public OldPasswordInvalid(String message){
        super(message);
    }

    public OldPasswordInvalid(Throwable cause){
        super(cause);
    }
}
