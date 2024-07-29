package com.felipe.controller;

import com.felipe.interfaces.IReservationDTO;
import com.felipe.model.dto.BookDTO;
import com.felipe.model.dto.NewReservationDTO;
import com.felipe.model.dto.ReservationDTO;
import com.felipe.model.entity.Reservation;
import com.felipe.service.ReservationService;
import com.felipe.service.exception.BookAlreadyRentedException;
import org.apache.coyote.Response;
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

    @GetMapping("/book/{id}")
    public ResponseEntity<List<ReservationDTO>> getReservationByBookId(@PathVariable Integer id){
        List<ReservationDTO> reservationDTOList = service.getReservationByBookId(id);
        return ResponseEntity.status(HttpStatus.OK).body(reservationDTOList);
    }


    @GetMapping("/isRented/{id}")
    public ResponseEntity<Boolean> isBookRented(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(service.isRented(id));
    }

    @GetMapping("/open")
    public ResponseEntity<List<ReservationDTO>> getAllOpenReservations(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getOpenReservations());
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> newReservation(@RequestBody NewReservationDTO reservation){
        NewReservationDTO newReservationDTO = NewReservationDTO.builder()
                .customerId(reservation.getCustomerId())
                .bookId(reservation.getBookId())
                .build();
        ReservationDTO createdReservation = service.registerNewReservation(newReservationDTO);
        return ResponseEntity.status(HttpStatus.OK).body(createdReservation);
    }

    @PostMapping("/postpone/{id}")
    public ResponseEntity<ReservationDTO> postponeReservation(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(service.postponeReservation(id));
    }


    @PutMapping("/bookDevolution/{id}")
    public ReservationDTO confirmBookDevolution(@PathVariable Integer id){
        return service.confirmBookDevolution(id);
    }

}

