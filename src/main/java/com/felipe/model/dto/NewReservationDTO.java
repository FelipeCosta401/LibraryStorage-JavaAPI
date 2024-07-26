package com.felipe.model.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewReservationDTO {
    private Integer bookId;
    private Integer customerId;
}
