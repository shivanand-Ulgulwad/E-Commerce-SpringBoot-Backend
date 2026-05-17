package com.app.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.app.dto.ApiResponseDTO;
import com.app.dto.UserRequestDTO;
import com.app.dto.UserResponseDTO;
import com.app.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/users")
@Tag(name = "User APIs", description = "User profile and account management APIs")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;




    @Operation(
            summary = "Get all users",
            description = "ADMIN only endpoint to fetch all users"
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>> getAll() {

        return ResponseEntity.ok(
                new ApiResponseDTO<>("Users fetched", service.getAll())
        );
    }

    @Operation(
            summary = "Get user by Id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponseDTO<>("User fetched", service.getById(id))
        );
    }

    @Operation(
            summary = "Delete user by Id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.ok(
                new ApiResponseDTO<>("User deleted", null)
        );
    }
}
