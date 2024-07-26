package com.felipe.repository;

import com.felipe.interfaces.IReservationDTO;
import com.felipe.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT\n" +
            "\tr.id `reservationId`,\n" +
            "    r.`date` `reservationDate`,\n" +
            "    r.devolution_date `reservationDevolutionDate`,\n" +
            "    r.was_finished `wasReservationFinished`,\n" +
            "    b.`name` `bookName`,\n" +
            "    b.author `bookAuthor`,\n" +
            "    c.`name` `customerName`,\n" +
            "    c.email `customerEmail`\n" +
            "FROM reservation r \n" +
            "INNER JOIN book b on r.book_id = b.id\n" +
            "INNER JOIN customer c on r.customer_id = c.id\n" +
            "WHERE r.customer_id = :id;\n", nativeQuery = true)
    List<IReservationDTO> getAllCustomersReservation(@Param("id") Integer id);
}
