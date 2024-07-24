package com.felipe.service;

import com.felipe.interfaces.IReservationDTO;
import com.felipe.model.dto.NewReservationDTO;
import com.felipe.model.dto.ReservationDTO;
import com.felipe.model.entity.Reservation;
import com.felipe.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        Reservation createdReservation = repository.save(newReservation);
        Integer reservationId = createdReservation.getId();
        IReservationDTO iReservation = repository.findReservationById(reservationId);
        ReservationDTO reservationDTO = iReservation.toReservationDTO();
        return reservationDTO;

    }
}
