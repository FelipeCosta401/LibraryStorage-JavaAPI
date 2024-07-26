package com.felipe.repository;

import com.felipe.interfaces.IReservationDTO;
import com.felipe.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Boolean existsByBookId(Integer id);

    @Query(value = "SELECT \n" +
            "\t\tr.id `reservationId`,\n" +
            "        r.`date` `reservationDate`,\n" +
            "        r.devolution_date `reservationDevolutionDate`,\n" +
            "        r.was_finished `wasReservationFinished`,\n" +
            "        b.`name` `bookName`,\n" +
            "        b.author `bookAuthor`,\n" +
            "        c.`name` `customerName`,\n" +
            "\t\tc.email `customerEmail`\n" +
            "FROM reservation r \n" +
            "INNER JOIN book b ON r.book_id = b.id\n" +
            "INNER JOIN customer c ON r.customer_id = c.id\n" +
            "ORDER BY r.`date` DESC;", nativeQuery = true)
    List<IReservationDTO> findAllReservations();

    @Query(value = "SELECT \n" +
            "\t\tr.id `reservationId`,\n" +
            "        r.`date` `reservationDate`,\n" +
            "        r.devolution_date `reservationDevolutionDate`,\n" +
            "        r.was_finished `wasReservationFinished`,\n" +
            "        b.`name` `bookName`,\n" +
            "        b.author `bookAuthor`,\n" +
            "        c.`name` `customerName`,\n" +
            "\t\tc.email `customerEmail`\n" +
            "FROM reservation r \n" +
            "INNER JOIN book b ON r.book_id = b.id\n" +
            "INNER JOIN customer c ON r.customer_id = c.id\n" +
            "WHERE r.id = :id;", nativeQuery = true)
    IReservationDTO findReservationById(@PathVariable Integer id);

    @Query(value = "SELECT was_finished FROM reservation r WHERE r.book_id = :id ORDER BY `date` DESC LIMIT 1;", nativeQuery = true)
    Integer wasReservationFinished(@PathVariable Integer id);
}
