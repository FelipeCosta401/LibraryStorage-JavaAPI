package com.felipe.controller;

import com.felipe.model.dto.NewReservationDTO;
import com.felipe.model.dto.ReservationDTO;
import com.felipe.model.entity.Reservation;
import com.felipe.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService service;

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getReservations(){
        List<ReservationDTO> reservationsDTOlist = service.getAllReservations();
        return ResponseEntity.status(HttpStatus.OK).body(reservationsDTOlist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Integer id){
        ReservationDTO reservationFound = service.getReservationById(id);
        return ResponseEntity.status(HttpStatus.OK).body(reservationFound);
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> newReservation(@RequestBody NewReservationDTO reservation){
        NewReservationDTO reservationDTO = NewReservationDTO.builder()
                .bookId(reservation.getBookId())
                .customerId(reservation.getCustomerId())
                .build();
        ReservationDTO createdReservation = service.registerNewReservation(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

}

