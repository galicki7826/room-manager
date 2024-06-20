package com.dgalicki.room.occupancy.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CalculationRequestDto(@NotNull @Min(0) Integer numberOfPremiumRooms,
                                    @NotNull @Min(0) Integer numberOfEconomyRooms) {
}