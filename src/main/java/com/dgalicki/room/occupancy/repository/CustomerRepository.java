package com.dgalicki.room.occupancy.repository;

import com.dgalicki.room.occupancy.model.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c.price FROM Customer c WHERE c.price >= :premiumBoundary ORDER BY c.price DESC")
    List<BigDecimal> findPremiumCustomersPrices(@Param("premiumBoundary") BigDecimal premiumBoundary, Pageable pageable);

    @Query("SELECT c.price FROM Customer c WHERE c.price < :premiumBoundary ORDER BY c.price DESC")
    List<BigDecimal> findEconomyCustomersPrices(@Param("premiumBoundary") BigDecimal premiumBoundary, Pageable pageable);

}