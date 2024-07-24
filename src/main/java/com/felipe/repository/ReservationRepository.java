package com.felipe.repository;

import com.felipe.interfaces.IReservationDTO;
import com.felipe.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query(value = "SELECT \n" +
            "\tr.id `reservationId`,\n" +
            "    r.`date` `reservationDate`,\n" +
            "    r.devolution_date `reservationDevolutionDate`,\n" +
            "    b.`name` `bookName`,\n" +
            "    b.author `bookAuthor`,\n" +
            "    c.`name` `customerName`,\n" +
            "    c.email `customerEmail`\n" +
            "FROM reservation r \n" +
            "INNER JOIN book b ON r.id = b.id \n" +
            "INNER JOIN customer c ON r.customer_id = c.id;", nativeQuery = true)
    List<IReservationDTO> findAllReservations();

    @Query(value = "SELECT \n" +
            "\tr.id `reservationId`,\n" +
            "    r.`date` `reservationDate`,\n" +
            "    r.devolution_date `reservationDevolutionDate`,\n" +
            "    b.`name` `bookName`,\n" +
            "    b.author `bookAuthor`,\n" +
            "    c.`name` `customerName`,\n" +
            "    c.email `customerEmail`\n" +
            "FROM reservation r \n" +
            "INNER JOIN book b ON r.id = b.id \n" +
            "INNER JOIN customer c ON r.customer_id = c.id\n" +
            "WHERE r.id = :id ;", nativeQuery = true)
    IReservationDTO findReservationById(@PathVariable Integer id);
}
