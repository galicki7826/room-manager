package com.dgalicki.room.occupancy.model.entity;

import java.math.BigDecimal;

public record CalculationResult(BigDecimal premiumRoomsRevenue, BigDecimal economyRoomsRevenue,
                                int occupiedPremiumRooms, int occupiedEconomyRooms
) {
}
