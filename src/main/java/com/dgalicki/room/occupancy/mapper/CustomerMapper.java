package com.dgalicki.room.occupancy.mapper;

import com.dgalicki.room.occupancy.model.dto.CustomerDto;
import com.dgalicki.room.occupancy.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toDto(Customer customer);

    @Mapping(source = "price", target = "price", qualifiedByName = "mapPrice")
    Customer toCustomer(CustomerDto customerDto);

    @Named("mapPrice")
    default BigDecimal mapPaymentAmount(BigDecimal paymentAmount) {
        return paymentAmount.setScale(2, RoundingMode.UP);
    }
}
