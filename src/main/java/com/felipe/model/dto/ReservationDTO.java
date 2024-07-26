package com.felipe.model.dto;

import com.felipe.model.entity.Book;
import com.felipe.model.entity.Customer;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Integer id;
    private Timestamp date;
    private Timestamp devolutionDate;
    private Integer wasFinished;
    private BookDTO book;
    private CustomerDTO customer;
}
