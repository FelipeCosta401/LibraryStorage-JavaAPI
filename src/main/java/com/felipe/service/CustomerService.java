package com.felipe.service;

import com.felipe.interfaces.IReservationDTO;
import com.felipe.model.dto.ReservationDTO;
import com.felipe.model.entity.Customer;
import com.felipe.model.entity.Reservation;
import com.felipe.repository.CustomerRepository;
import com.felipe.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public List<Customer> getAllCustomers(){
        return repository.findAll();
    }

    public Customer registerCustomer(Customer customer) {
        return repository.save(customer);
    }

    public Customer getCustomerById(Integer id) {
        Optional<Customer> optFoundCustomer = repository.findById(id);
        if(optFoundCustomer.isPresent()){
            Customer foundCustomer = optFoundCustomer.get();
            return foundCustomer;
        } else{
            throw new NotFoundException("User not find with this id!");
        }
    }

    public Customer updateCustomer(Customer newCustomer, Integer id) {
        Customer customer = repository.findById(id).get();
        customer.setName(newCustomer.getName());
        customer.setCpf(newCustomer.getCpf());
        customer.setEmail(newCustomer.getEmail());
        return repository.save(customer);
    }


    public List<ReservationDTO> getAllCustomersReservation(Integer id) {
        List<IReservationDTO> iReservationDTOList = repository.getAllCustomersReservation(id);
        List<ReservationDTO> reservationDTOlist = new ArrayList<>();
        for(IReservationDTO r : iReservationDTOList){
            reservationDTOlist.add(r.toReservationDTO());
        }
        return reservationDTOlist;
    }

    public Boolean customerExists(Integer id){
        return repository.existsById(id);
    }
}
