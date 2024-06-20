package com.dgalicki.room.occupancy.service;

import com.dgalicki.room.occupancy.model.entity.CalculationResult;
import com.dgalicki.room.occupancy.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculationServiceImpl implements CalculationService {

    private final CustomerRepository customerRepository;
    @Value("#{new java.math.BigDecimal('${premium.boundary:100}')}")
    private BigDecimal premiumBoundary;

    @Override
    public CalculationResult calculate(int premiumRoomsCount, int economyRoomsCount) {
        List<BigDecimal> premiumGuestsPrices = fetchPremiumGuestsPrices(premiumRoomsCount);
        List<BigDecimal> economyGuestsPrices = fetchEconomyGuestsPrices(premiumRoomsCount, economyRoomsCount);

        int premiumRoomsAllocated = CalculationHelper.calculatePremiumRoomsAllocated(premiumRoomsCount, premiumGuestsPrices.size());
        int premiumRoomsLeft = CalculationHelper.calculateRoomsLeft(premiumRoomsCount, premiumRoomsAllocated);
        int extraGuestsForEconomy = CalculationHelper.calculateExtraGuestsForEconomy(economyGuestsPrices.size(), economyRoomsCount);
        int numberOfUpgrades = CalculationHelper.calculateNumberOfUpgrades(premiumRoomsLeft, extraGuestsForEconomy);
        int regularEconomyRooms = CalculationHelper.calculateRegularEconomyRooms(economyGuestsPrices.size(), numberOfUpgrades, economyRoomsCount);

        BigDecimal premiumRoomsAmount = CalculationHelper.calculateTotalAmount(premiumGuestsPrices, premiumRoomsAllocated);
        BigDecimal upgradedRoomsAmount = CalculationHelper.calculateTotalAmount(economyGuestsPrices, numberOfUpgrades);
        BigDecimal economyRoomsAmount = CalculationHelper.calculateTotalAmount(economyGuestsPrices, regularEconomyRooms, numberOfUpgrades);

        return new CalculationResult(
                premiumRoomsAmount.add(upgradedRoomsAmount),
                economyRoomsAmount,
                premiumRoomsAllocated + numberOfUpgrades,
                regularEconomyRooms
        );
    }

    private List<BigDecimal> fetchPremiumGuestsPrices(int premiumRoomsCount) {
        if (premiumRoomsCount == 0) {
            return Collections.emptyList();
        }
        Pageable pageRequestPremium = PageRequest.of(0, premiumRoomsCount);
        return customerRepository.findPremiumCustomersPrices(premiumBoundary, pageRequestPremium);
    }

    private List<BigDecimal> fetchEconomyGuestsPrices(int premiumRoomsCount, int economyRoomsCount) {
        if (premiumRoomsCount + economyRoomsCount == 0) {
            return Collections.emptyList();
        }
        Pageable pageRequestEconomy = PageRequest.of(0, premiumRoomsCount + economyRoomsCount);
        return customerRepository.findEconomyCustomersPrices(premiumBoundary, pageRequestEconomy);
    }

}