package com.dgalicki.room.occupancy.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Utility class that provides methods for calculating room occupancy and revenue.
 * This class cannot be instantiated and its methods are static.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CalculationHelper {

    /**
     * Calculates the number of premium rooms that can be allocated based on available rooms and premium guests.
     *
     * @param premiumRoomsCount  the total number of premium rooms available
     * @param premiumGuestsCount the total number of premium guests
     * @return the number of premium rooms that can be allocated
     */
    static int calculatePremiumRoomsAllocated(int premiumRoomsCount, int premiumGuestsCount) {
        return Math.min(premiumRoomsCount, premiumGuestsCount);
    }

    /**
     * Calculates the number of premium rooms left unoccupied after allocation.
     *
     * @param premiumRoomsCount     the total number of premium rooms available
     * @param premiumRoomsAllocated the number of premium rooms that have been allocated
     * @return the number of premium rooms left unoccupied
     */
    static int calculateRoomsLeft(int premiumRoomsCount, int premiumRoomsAllocated) {
        return Math.max(premiumRoomsCount - premiumRoomsAllocated, 0);
    }

    /**
     * Calculates the number of extra guests for economy rooms based on available rooms and economy guests.
     *
     * @param economyGuestsCount the total number of economy guests
     * @param economyRoomsCount  the total number of economy rooms available
     * @return the number of extra economy guests that cannot be accommodated in economy rooms
     */
    static int calculateExtraGuestsForEconomy(int economyGuestsCount, int economyRoomsCount) {
        return Math.max(economyGuestsCount - economyRoomsCount, 0);
    }

    /**
     * Calculates the number of economy guests that can be upgraded to premium rooms.
     *
     * @param premiumRoomsLeft      the number of premium rooms left unoccupied
     * @param extraGuestsForEconomy the number of extra economy guests that cannot be accommodated in economy rooms
     * @return the number of economy guests that can be upgraded to premium rooms
     */
    static int calculateNumberOfUpgrades(int premiumRoomsLeft, int extraGuestsForEconomy) {
        return Math.min(premiumRoomsLeft, extraGuestsForEconomy);
    }

    /**
     * Calculates the number of regular economy rooms that can be occupied, excluding upgrades.
     *
     * @param economyGuestsCount the total number of economy guests
     * @param numberOfUpgrades   the number of economy guests that have been upgraded to premium rooms
     * @param economyRoomsCount  the total number of economy rooms available
     * @return the number of regular economy rooms that can be occupied
     */
    static int calculateRegularEconomyRooms(int economyGuestsCount, int numberOfUpgrades, int economyRoomsCount) {
        return Math.min(economyGuestsCount - numberOfUpgrades, economyRoomsCount);
    }

    /**
     * Calculates the total amount of money collected from a given list of guest prices, limited to a specified number of guests.
     *
     * @param guestPrices the list of guest prices
     * @param limit       the maximum number of guest prices to include in the calculation
     * @return the total amount collected, rounded down to 2 decimal places
     */
    static BigDecimal calculateTotalAmount(List<BigDecimal> guestPrices, int limit) {
        return guestPrices.stream().limit(limit)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.UP);
    }

    /**
     * Calculates the total amount of money collected from a given list of guest prices, limited to a specified number of guests and skipping a specified number of guests.
     *
     * @param guestPrices the list of guest prices
     * @param limit       the maximum number of guest prices to include in the calculation
     * @param skip        the number of guest prices to skip before starting the calculation
     * @return the total amount collected, rounded down to 2 decimal places
     */
    static BigDecimal calculateTotalAmount(List<BigDecimal> guestPrices, int limit, int skip) {
        return guestPrices.stream().skip(skip).limit(limit)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.UP);
    }

}