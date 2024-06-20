package com.dgalicki.room.occupancy.controller;

import com.dgalicki.room.occupancy.model.dto.CalculationRequestDto;
import com.dgalicki.room.occupancy.model.dto.CalculationResultDto;
import com.dgalicki.room.occupancy.model.entity.CalculationResult;
import com.dgalicki.room.occupancy.mapper.RoomOccupancyMapper;
import com.dgalicki.room.occupancy.model.dto.ErrorMessageDto;
import com.dgalicki.room.occupancy.service.CalculationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room-occupancy")
@Slf4j
@Validated
public class RoomOccupancyController {

    private final CalculationServiceImpl roomOccupancyService;
    private final RoomOccupancyMapper roomOccupancyMapper;

    @Operation(
            summary = "Calculate room occupancy and revenue",
            description = "This endpoint receives the number of available premium and economy rooms and calculates the optimal " +
                    "room occupancy and total revenue. It returns the number of occupied rooms in each category and the total revenue generated.",
            tags = {"Room Occupancy"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Calculation successful. Returns the room occupancy and revenue details.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CalculationResultDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request. The input parameters might be incorrect or missing.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class))
            )
    })
    @PostMapping("/calculate")
    public CalculationResultDto calculate(@RequestBody @Valid CalculationRequestDto calculationRequestDto) {
        log.info("Received request to calculate room occupancy and revenue with data: {}", calculationRequestDto);
        CalculationResult calculationResult = roomOccupancyService.calculate(calculationRequestDto.numberOfPremiumRooms(), calculationRequestDto.numberOfEconomyRooms());
        CalculationResultDto calculationResultDto = roomOccupancyMapper.toDto(calculationResult);
        log.info("Calculated room occupancy and revenue successfully: {}", calculationResultDto);
        return calculationResultDto;
    }

}