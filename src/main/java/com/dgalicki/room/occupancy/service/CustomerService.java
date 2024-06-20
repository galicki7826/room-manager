package com.dgalicki.room.occupancy.service;

import com.dgalicki.room.occupancy.model.entity.Customer;

public interface CustomerService {

    /**
     * Saves a customer to the repository.
     *
     * @param customer the customer to save
     * @return the saved customer
     */
    Customer saveCustomer(Customer customer);
}
