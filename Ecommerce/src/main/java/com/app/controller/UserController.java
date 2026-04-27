package com.app.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserController {

    private final UserService service;




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
