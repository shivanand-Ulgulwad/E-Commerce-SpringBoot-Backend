package com.app.controller;

import com.app.config.JwtUtility;
import com.app.dto.*;
import com.app.service.CustomUserDetailsService;
import com.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication APIs", description = "User registration, login, and authentication management")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class AuthController {


    private final UserService userService;
    private final JwtUtility jwtUtility;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with USER role by default"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<String>> register(@RequestBody UserRequestDTO user){
        return new ResponseEntity<>(
                new ApiResponseDTO<>(
                        "User Registered Successfully",
                            userService.register(user)
                ), HttpStatus.CREATED
        );
    }

    @Operation(
            summary = "Login user",
            description = "Authenticates user and returns JWT token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<String>> login(@Valid @RequestBody LoginRequestDTO user){

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            System.out.println("LOGIN SUCCESS");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return new ResponseEntity<>(
                new ApiResponseDTO<>(
                        "Login Successful",
                                jwtUtility.generateToken(user.getEmail())
                ),HttpStatus.OK
        );
    }

}
