package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.UserRequestDTO;
import com.app.dto.UserResponseDTO;
import com.app.entity.User;
import com.app.mapper.UserMapper;
import com.app.repository.UserRepository;
import com.app.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	 @Autowired
	    private UserRepository userRepo;

	    @Override
	    public UserResponseDTO create(UserRequestDTO dto) {
            User existingUser = userRepo.findByEmail(dto.getEmail()).orElse(null);

            if (existingUser != null) {
                // 👉 LOGIN FLOW (return existing user)
                return mapToDTO(existingUser);
            }

	        User user = new User();
	        user.setName(dto.getName());
	        user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword());

	        User saved = userRepo.save(user);

	        return mapToDTO(saved);
	    }

	    @Override
	    public List<UserResponseDTO> getAll() {

	        return userRepo.findAll()
	                .stream()
	                .map(this::mapToDTO)
	                .toList();
	    }

	    @Override
	    public UserResponseDTO getById(Long id) {

	        User user = userRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        return mapToDTO(user);
	    }

	    @Override
	    public void delete(Long id) {

	        User user = userRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        userRepo.delete(user);
	    }

	    // 🔥 Mapper inside service (simple approach)
	    private UserResponseDTO mapToDTO(User user) {

	        UserResponseDTO dto = new UserResponseDTO();
	        dto.setId(user.getId());
	        dto.setName(user.getName());
	        dto.setEmail(user.getEmail());

	        return dto;
	    }
}
