package com.mizaniyati.mapper;

import com.mizaniyati.dto.RegisterRequestDTO;
import com.mizaniyati.dto.UserResponseDTO;
import com.mizaniyati.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true) // On ne mappe pas le password ici
    User toEntity(RegisterRequestDTO dto);

    UserResponseDTO toResponseDto(User user);
}
