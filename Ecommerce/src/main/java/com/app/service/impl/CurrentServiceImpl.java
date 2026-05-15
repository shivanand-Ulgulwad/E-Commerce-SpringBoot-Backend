package com.app.service.impl;

import com.app.entity.User;
import com.app.exception.UserNotFoundException;
import com.app.repository.UserRepository;
import com.app.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentServiceImpl implements CurrentUserService {

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String email = authentication.getName();

        return userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found"));
    }
}
