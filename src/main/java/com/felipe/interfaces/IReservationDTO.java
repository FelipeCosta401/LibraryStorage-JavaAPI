package com.felipe.interfaces;

import com.felipe.model.dto.BookDTO;
import com.felipe.model.dto.CustomerDTO;
import com.felipe.model.dto.ReservationDTO;

import java.sql.Timestamp;

public interface IReservationDTO {

    //Reservations's responses
    public Integer getReservationId();
    public Timestamp getReservationDate();
    public Timestamp getReservationDevolutionDate();
    public Integer getWasReservationFinished();

    //Book's responses
    public String getBookName();
    public String getBookAuthor();

    //Customer's responses
    public String getCustomerName();
    public String getCustomerEmail();

    public default ReservationDTO toReservationDTO(){
        return ReservationDTO.builder()
                .id(this.getReservationId())
                .date(this.getReservationDate())
                .devolutionDate(this.getReservationDevolutionDate())
                .wasFinished(this.getWasReservationFinished())
                .book(BookDTO.builder()
                        .name(this.getBookName())
                        .author(this.getBookAuthor())
                        .build())
                .customer(CustomerDTO.builder()
                        .name(this.getCustomerName())
                        .email(this.getCustomerEmail())
                        .build())
                .build();
    }
}
