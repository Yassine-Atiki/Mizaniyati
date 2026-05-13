package com.mizaniyati.mapper;

import com.mizaniyati.dto.BudgetRequestDTO;
import com.mizaniyati.dto.BudgetResponseDTO;
import com.mizaniyati.entity.Budget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface BudgetMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "spentAmount", ignore = true) // Set manually or default to 0
    Budget toEntity(BudgetRequestDTO dto);

    @Mapping(target = "categoryId", source = "category.id")
    BudgetResponseDTO toResponseDto(Budget budget);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "spentAmount", ignore = true) // Do not override spent amount on update unless explicitly needed
    void updateEntityFromDto(BudgetRequestDTO dto, @MappingTarget Budget entity);
}
