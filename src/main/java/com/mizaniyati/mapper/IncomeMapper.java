package com.mizaniyati.mapper;

import com.mizaniyati.dto.IncomeRequestDTO;
import com.mizaniyati.dto.IncomeResponseDTO;
import com.mizaniyati.entity.Income;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface IncomeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true) // Will be handled in service if categorId exists
    Income toEntity(IncomeRequestDTO dto);

    @Mapping(target = "categoryId", source = "category.id")
    IncomeResponseDTO toResponseDto(Income income);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateEntityFromDto(IncomeRequestDTO dto, @MappingTarget Income entity);
}
