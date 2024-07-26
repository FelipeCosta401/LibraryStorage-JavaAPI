package com.felipe.service.exception;

public class CustomerHasOpenReservation extends RuntimeException{
    public CustomerHasOpenReservation(String message){
        super(message);
    }

}
