package com.felipe.service;

import com.felipe.interfaces.IReservationDTO;
import com.felipe.model.dto.NewReservationDTO;
import com.felipe.model.dto.ReservationDTO;
import com.felipe.model.entity.Reservation;
import com.felipe.repository.ReservationRepository;
import com.felipe.service.exception.BookAlreadyRentedException;
import com.felipe.service.exception.CustomerHasOpenReservation;
import com.felipe.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository repository;

    @Autowired
    private BookService bookService;

    @Autowired
    private CustomerService customerService;

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
        //Verifies if reservation exists
        Boolean reservationExists = repository.existsById(id);
        if(reservationExists){
            IReservationDTO iReservation = repository.findReservationById(id);
            return iReservation.toReservationDTO();
        }
        throw new NotFoundException("Reservation not found!");
    }

    public ReservationDTO registerNewReservation(NewReservationDTO reservation) {
        //Verifies if book exists
        Boolean bookExists = this.bookExists(reservation.getBookId());
        if(bookExists){
            //Verifies if customer exists
            Boolean customerExists = customerService.customerExists(reservation.getCustomerId());
            if(customerExists){
                //Verifies if the book is available for rent
                Boolean isBookAlreadyRented = this.isRented(reservation.getBookId());
                //If book is not available, throw exception
                if(isBookAlreadyRented){
                    throw new BookAlreadyRentedException("Book is already rented");
                } else{
                    //Verifies if customer is able to make a new reservation
                    Integer customerId = reservation.getCustomerId();
                    Integer canCustomerMakeReservation = repository.verifyCustomerStatus(customerId);
                    if(canCustomerMakeReservation == 1){
                        return saveNewReservation(reservation);
                    }else{
                        throw new CustomerHasOpenReservation("Customer already has an open reservation!");
                    }
                }
            } else{
             throw new NotFoundException("Customer not found!");
            }
        } else{
            throw new NotFoundException("Book not found!");
        }
    }

    public Boolean isRented(Integer id) {
        //Verifies if book exists
        if(this.bookExists(id)) {
            //Verifies if exists any reservation with this book
            Boolean isThereReservationWithThisBook = repository.existsByBookId(id);
            if (isThereReservationWithThisBook) {
                //Verifies if the reservation is not done
                Integer wasLastReservationFinished = repository.wasReservationFinished(id);
                if (wasLastReservationFinished == 1) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
        throw new NotFoundException("Book not found!");
    }

    public ReservationDTO confirmBookDevolution(Integer id) {
        //Verifies if reservation exists
        if(repository.existsById(id)){
            //Find the reservation
            Reservation reservationFound = repository.findById(id).get();

            //Set the reservation status to finished
            reservationFound.setWasFinished(1);

            //Save new reservation status
            repository.save(reservationFound);

            //Returns new reservationDTO
            IReservationDTO updatedReservation = repository.findReservationById(id);
            return updatedReservation.toReservationDTO();
        }
        throw new NotFoundException("Reservation not found!");
    }

    private ReservationDTO saveNewReservation(NewReservationDTO reservation){
        //Rescue the reservation and devolution's date
        Timestamp currentDay = new Timestamp(System.currentTimeMillis());
        Timestamp nextMonth = calculateDevolutionDate(currentDay);

        Reservation newReservation = new Reservation();
        newReservation.setBookId(reservation.getBookId());
        newReservation.setCustomerId(reservation.getCustomerId());
        newReservation.setDate(currentDay);
        newReservation.setDevolutionDate(nextMonth);
        newReservation.setWasFinished(0);

        repository.save(newReservation);
        IReservationDTO iReservationDTO = repository.findReservationById(newReservation.getId());
        return iReservationDTO.toReservationDTO();
    }

    private Timestamp calculateDevolutionDate(Timestamp reservationDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reservationDate);
        calendar.add(Calendar.MONTH, 1);
        return new Timestamp(calendar.getTimeInMillis());
    }

    private Boolean bookExists(Integer id){
        if(bookService.bookExists(id))
            return true;
        return false;
    }



}
