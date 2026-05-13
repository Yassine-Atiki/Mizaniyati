package com.mizaniyati.mapper;

import com.mizaniyati.dto.CategoryDTO;
import com.mizaniyati.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")  // ← Spring peut l'injecter avec @Autowired
public interface CategoryMapper {


    CategoryDTO toDTO(Category category);

    // DTO → Entity
    // createdBy n'est pas dans le DTO
    // le Service va le setter manuellement après
    @Mapping(target = "createdBy", ignore = true)
    Category toEntity(CategoryDTO dto);
}