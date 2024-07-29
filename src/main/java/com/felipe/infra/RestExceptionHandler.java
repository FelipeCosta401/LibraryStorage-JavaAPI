package com.felipe.infra;

import com.felipe.service.exception.CustomerHasOpenReservation;
import com.felipe.service.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<String> customerNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado com esse id!");
    }

    @ExceptionHandler(CustomerHasOpenReservation.class)
    private ResponseEntity<String> customerHasOpenReservation(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("O usuário tem uma reserva não finalizada!");
    }

}
