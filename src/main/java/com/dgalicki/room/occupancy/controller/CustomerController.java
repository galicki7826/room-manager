package com.dgalicki.room.occupancy.controller;

import com.dgalicki.room.occupancy.service.CustomerServiceImpl;
import com.dgalicki.room.occupancy.mapper.CustomerMapper;
import com.dgalicki.room.occupancy.model.dto.CustomerDto;
import com.dgalicki.room.occupancy.model.dto.ErrorMessageDto;
import com.dgalicki.room.occupancy.model.entity.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@Slf4j
@Validated
public class CustomerController {

    private final CustomerServiceImpl customerService;
    private final CustomerMapper customerMapper;

    @Operation(
            summary = "Save customer",
            description = "This endpoint receives customer details and saves the customer information in the system. It returns the saved customer details.",
            tags = {"Customer"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Customer created successfully. Returns the details of the saved customer.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomerDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request. The input parameters might be incorrect or missing.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class))
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerDto saveCustomer(@RequestBody @Valid CustomerDto customerDto) {
        log.info("Received request to save customer with data: {}", customerDto);
        Customer savedCustomer = customerService.saveCustomer(customerMapper.toCustomer(customerDto));
        CustomerDto customerResponse = customerMapper.toDto(savedCustomer);
        log.info("Saved customer successfully: {}", customerResponse);
        return customerResponse;
    }

}