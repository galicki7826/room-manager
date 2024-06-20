package com.dgalicki.room.occupancy.service;

import com.dgalicki.room.occupancy.model.entity.CalculationResult;

public interface CalculationService {

    /**
     * Calculates the room occupancy and revenue based on the number of available premium and economy rooms.
     *
     * @param premiumRoomsCount the number of premium rooms available
     * @param economyRoomsCount the number of economy rooms available
     * @return the calculation result containing occupied rooms and revenue details
     */
    CalculationResult calculate(int premiumRoomsCount, int economyRoomsCount);
}
