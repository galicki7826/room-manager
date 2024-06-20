package com.dgalicki.room.occupancy.service;

import com.dgalicki.room.occupancy.model.entity.Customer;
import com.dgalicki.room.occupancy.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void whenSaveCustomerCalled_thenReturnSavedCustomer() {
        Customer customer = new Customer(1L, BigDecimal.valueOf(200.0));
        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        Customer savedCustomer = customerService.saveCustomer(customer);

        verify(customerRepository).save(customer);
        assertEquals(customer, savedCustomer);
    }

}
