package com.dgalicki.room.occupancy.model.dto;

public record CalculationResultDto(Double premiumRoomsRevenue, Double economyRoomsRevenue,
                                   int occupiedPremiumRooms, int occupiedEconomyRooms
) {
}
