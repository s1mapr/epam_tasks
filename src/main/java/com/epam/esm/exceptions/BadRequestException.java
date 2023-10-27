package com.epam.esm.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String errorMessage){
        super(errorMessage);
    }
}
