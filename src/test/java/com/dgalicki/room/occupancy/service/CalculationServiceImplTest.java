package com.dgalicki.room.occupancy.service;

import com.dgalicki.room.occupancy.model.entity.CalculationResult;
import com.dgalicki.room.occupancy.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CalculationServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CalculationServiceImpl roomOccupancyService;

    @BeforeEach
    void setUp() {
        when(customerRepository.findPremiumCustomersPrices(any(), any())).thenReturn(mockPremiumPrices());
        when(customerRepository.findEconomyCustomersPrices(any(), any())).thenReturn(mockEconomyPrices());
        ReflectionTestUtils.setField(roomOccupancyService, "premiumBoundary", new BigDecimal(100));
    }

    @Test
    void shouldCalculateOccupancyAndRevenueWhenAllRoomsOccupied() {
        CalculationResult expectedResult = new CalculationResult(new BigDecimal("738.00").setScale(2), new BigDecimal("167.99").setScale(2),3, 3);
        CalculationResult actualResult = roomOccupancyService.calculate(3, 3);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldCalculateOccupancyAndRevenueWhenPartiallyOccupied() {
        CalculationResult expectedResult = new CalculationResult(new BigDecimal("1054.00").setScale(2), new BigDecimal("189.99").setScale(2), 6, 4);
        CalculationResult actualResult = roomOccupancyService.calculate(7, 5);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldCalculateOccupancyAndRevenueWhenOverbooked() {
        CalculationResult expectedResult = new CalculationResult(new BigDecimal("583.00").setScale(2), new BigDecimal("189.99").setScale(2), 2, 4);
        CalculationResult actualResult = roomOccupancyService.calculate(2, 7);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldCalculateOccupancyAndRevenueWhenNoGuests() {
        CalculationResult expectedResult = new CalculationResult(new BigDecimal(0).setScale(2), new BigDecimal(0).setScale(2), 0, 0);
        CalculationResult actualResult = roomOccupancyService.calculate(0, 0);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldCalculateOccupancyAndRevenueWhenNoRoomsAvailable() {
        CalculationResult expectedResult = new CalculationResult(new BigDecimal("1153.99").setScale(2), new BigDecimal("45.00").setScale(2),7, 1);
        CalculationResult actualResult = roomOccupancyService.calculate(7, 1);
        assertEquals(expectedResult, actualResult);
    }

    private List<BigDecimal> mockPremiumPrices() {
        return Stream.of(new BigDecimal(155), new BigDecimal(374), new BigDecimal(100), new BigDecimal(101), new BigDecimal(115), new BigDecimal(209))
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    private List<BigDecimal> mockEconomyPrices() {
        return Stream.of(new BigDecimal(23), new BigDecimal(45), new BigDecimal(22), new BigDecimal(99.99))
                .sorted(Comparator.reverseOrder())
                .toList();
    }
}