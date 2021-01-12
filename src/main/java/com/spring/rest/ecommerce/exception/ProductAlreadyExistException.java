package com.spring.rest.ecommerce.exception;

public class ProductAlreadyExistException extends RuntimeException{
        public ProductAlreadyExistException(String message, Throwable cause){
            super(message, cause);
        }

        public ProductAlreadyExistException(String message){
            super(message);
        }

        public ProductAlreadyExistException(Throwable cause){
            super(cause);
        }
}
