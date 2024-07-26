package com.felipe.service;

import com.felipe.interfaces.IReservationDTO;
import com.felipe.model.dto.NewReservationDTO;
import com.felipe.model.dto.ReservationDTO;
import com.felipe.model.entity.Reservation;
import com.felipe.repository.ReservationRepository;
import com.felipe.service.exception.BookAlreadyRentedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository repository;

    public List<ReservationDTO> getAllReservations() {
        List<IReservationDTO> reservationsList = repository.findAllReservations();
        List<ReservationDTO> reservationDTOList = new ArrayList<>();
        for(IReservationDTO r : reservationsList){
            ReservationDTO dto = r.toReservationDTO();
            reservationDTOList.add(dto);
        }
        return reservationDTOList;
    }

    public ReservationDTO getReservationById(Integer id){
        IReservationDTO iReservation = repository.findReservationById(id);
        return iReservation.toReservationDTO();
    }

    public ReservationDTO registerNewReservation(NewReservationDTO reservation) {
        //Verifies if the book is available for rent
        Boolean isBookAlreadyRented = this.isRented(reservation.getBookId());
        //If book is available, register the reservation
        if(!isBookAlreadyRented){

            //Rescue the current day
            Timestamp currentDay = new Timestamp(System.currentTimeMillis());
            //Calcs the date before a month from now
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDay);
            calendar.add(Calendar.MONTH, 1);
            Timestamp nextMonth = new Timestamp(calendar.getTimeInMillis());

            Reservation newReservation = new Reservation();
            newReservation.setBookId(reservation.getBookId());
            newReservation.setCustomerId(reservation.getCustomerId());
            newReservation.setDate(currentDay);
            newReservation.setDevolutionDate(nextMonth);
            newReservation.setWasFinished(0);

            Reservation savedReservation = repository.save(newReservation);
            Integer reservationId = savedReservation.getId();
            IReservationDTO iReservationDTO = repository.findReservationById(reservationId);
            ReservationDTO createdReservation = iReservationDTO.toReservationDTO();

            return createdReservation;
        }
        //If the book is not available, throw exception
        throw new BookAlreadyRentedException("Book is already rented");
    }

    public Boolean isRented(Integer id) {
        Boolean isThereReservationWithThisBook = repository.existsByBookId(id);
        if(isThereReservationWithThisBook){
            Integer wasLastReservationFinished = repository.wasReservationFinished(id);
            if(wasLastReservationFinished == 1){
                return false;
            } else{
                return true;
            }
        } else{
            return false;
        }
    }

    public ReservationDTO confirmBookDevolution(Integer id) {
        Reservation reservationFound = repository.findById(id).get();
        reservationFound.setWasFinished(1);
        repository.save(reservationFound);
        IReservationDTO updatedReservation = repository.findReservationById(id);
        return updatedReservation.toReservationDTO();
    }
}
