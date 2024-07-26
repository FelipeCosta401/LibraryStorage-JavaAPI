package com.felipe.controller;

import com.felipe.model.dto.ReservationDTO;
import com.felipe.model.entity.Customer;
import com.felipe.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService service;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customersList = service.getAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(customersList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id){
        Customer foundCustomer = service.getCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundCustomer);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<List<ReservationDTO>> getAllCustomersReservation(@PathVariable Integer id){
        List<ReservationDTO> reservationDTOList = service.getAllCustomersReservation(id);
        return ResponseEntity.status(HttpStatus.OK).body(reservationDTOList);
    }

    @PostMapping
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer){
        Customer createdCustomer = service.registerCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody Customer newCustomer){
        Customer updatedCustomer = service.updateCustomer(newCustomer, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
    }
}
