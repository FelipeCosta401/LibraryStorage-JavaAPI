package com.felipe.service.exception;

public class BookAlreadyRentedException extends RuntimeException{
    public BookAlreadyRentedException(String message){
        super(message);
    }
}
