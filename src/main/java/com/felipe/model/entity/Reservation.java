package com.felipe.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "devolution_date", nullable = false)
    private Timestamp devolutionDate;

    @Column(name = "was_finished", nullable = false)
    private Integer wasFinished;
}
