package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.dto.ApiResponse;
import com.app.dto.UserRequestDTO;
import com.app.dto.UserResponseDTO;
import com.app.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/users")
public class UserController2 {
	@Autowired
    private UserService service;

    // ✅ CREATE USER
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<UserResponseDTO>> create(
            @Valid @RequestBody UserRequestDTO  dto) {

        return ResponseEntity
                .status(201)
                .body(new ApiResponse<>("User created", service.create(dto)));
    }

    // ✅ GET ALL USERS
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAll() {

        return ResponseEntity.ok(
                new ApiResponse<>("Users fetched", service.getAll())
        );
    }

    // ✅ GET USER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>("User fetched", service.getById(id))
        );
    }

    // ✅ DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.ok(
                new ApiResponse<>("User deleted", null)
        );
    }
}
