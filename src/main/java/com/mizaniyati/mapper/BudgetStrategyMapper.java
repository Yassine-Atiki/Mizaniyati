package com.mizaniyati.mapper;

import com.mizaniyati.dto.BudgetStrategyRequestDTO;
import com.mizaniyati.dto.BudgetStrategyResponseDTO;
import com.mizaniyati.entity.BudgetStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BudgetStrategyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "active", ignore = true) // Will default to false, set in service
    BudgetStrategy toEntity(BudgetStrategyRequestDTO dto);

    // active property maps to isActive transparently usually, we specify if needed mapping
    BudgetStrategyResponseDTO toResponseDto(BudgetStrategy entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateEntityFromDto(BudgetStrategyRequestDTO dto, @MappingTarget BudgetStrategy entity);
}

