package com.dgalicki.room.occupancy.mapper;

import com.dgalicki.room.occupancy.model.dto.CalculationResultDto;
import com.dgalicki.room.occupancy.model.entity.CalculationResult;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomOccupancyMapper {
    CalculationResultDto toDto(CalculationResult result);
}
