package com.felipe.model.dto;

import lombok.*;

import java.sql.Timestamp;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewReservationDTO {
    private Integer bookId;
    private Integer customerId;
    private Timestamp date;
    private Timestamp devolutionDate;
}
