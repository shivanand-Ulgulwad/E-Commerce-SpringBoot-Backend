package com.app.service.impl;

import java.util.List;

import com.app.config.JwtUtility;
import com.app.dto.LoginRequestDTO;
import com.app.dto.RegisterRequestDTO;
import com.app.entity.Role;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dto.UserRequestDTO;
import com.app.dto.UserResponseDTO;
import com.app.entity.User;

import com.app.repository.UserRepository;
import com.app.service.UserService;

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	    private final UserRepository userRepo;
        private final PasswordEncoder passwordEncoder;
        private final JwtUtility jwtUtility;

	    @Override
	    public String register(UserRequestDTO dto) {
            User existingUser = userRepo.findByEmail(dto.getEmail()).orElse(null);

            if (existingUser != null) {
                // 👉 LOGIN FLOW (return existing user)
                throw new RuntimeException("User is already Present");
            }

	        User user = new User();
	        user.setName(dto.getName());
	        user.setEmail(dto.getEmail());
            user.setPassword(passwordEncoder.encode( dto.getPassword()));
            user.setRole(Role.USER);

	        User saved = userRepo.save(user);

	        return "User is Registered with: "+dto.getEmail();
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
