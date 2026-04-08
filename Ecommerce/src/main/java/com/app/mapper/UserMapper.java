package com.app.mapper;

import com.app.dto.UserRequestDTO;
import com.app.dto.UserResponseDTO;
import com.app.entity.User;

public class UserMapper {
	
	 // DTO → Entity
    public static User toEntity(UserRequestDTO dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        return user;
    }

    // Entity → DTO
    public static UserResponseDTO toDTO(User user) {

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }

}
