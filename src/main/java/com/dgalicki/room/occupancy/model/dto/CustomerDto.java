package com.dgalicki.room.occupancy.model.dto;

import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record CustomerDto(@Min(0) BigDecimal price) {
}