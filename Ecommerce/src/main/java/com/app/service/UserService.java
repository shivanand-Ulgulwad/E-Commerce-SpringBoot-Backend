package com.app.service;

import java.util.List;

import com.app.dto.UserRequestDTO;
import com.app.dto.UserResponseDTO;

public interface UserService {
	 UserResponseDTO create(UserRequestDTO dto);

	    List<UserResponseDTO> getAll();

	    UserResponseDTO getById(Long id);

	    void delete(Long id);
}
