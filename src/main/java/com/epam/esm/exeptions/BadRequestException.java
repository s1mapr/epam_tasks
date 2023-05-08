package com.epam.esm.exeptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String errorMessage){
        super(errorMessage);
    }
}
