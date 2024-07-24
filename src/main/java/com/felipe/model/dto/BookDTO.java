package com.felipe.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private String name;
    private String author;
}
