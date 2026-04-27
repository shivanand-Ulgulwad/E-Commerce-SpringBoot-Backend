package com.app.controller;

import com.app.config.JwtUtility;
import com.app.dto.*;
import com.app.service.CustomUserDetailsService;
import com.app.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {


    private final UserService userService;
    private final JwtUtility jwtUtility;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody UserRequestDTO user){
        return new ResponseEntity<>(
                new ApiResponse<>(
                        "User Registered Successfully",
                            userService.register(user)
                ), HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequestDTO user){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        return new ResponseEntity<>(
                new ApiResponse<>(
                        "Login Successful",
                                jwtUtility.generateToken(user.getEmail())
                ),HttpStatus.OK
        );
    }

}
