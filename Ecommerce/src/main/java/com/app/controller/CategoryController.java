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
import com.app.dto.CategoryRequestDTO;
import com.app.dto.CategoryResponseDTO;
import com.app.service.CategoryService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/categories")
@Tag(name = "Category APIs", description = "Category management APIs for product grouping")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class CategoryController {
	

	    private final CategoryService service;

    @Operation(summary = "Create category")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> create(
	    		@Valid @RequestBody CategoryRequestDTO dto) {

	        return ResponseEntity
	                .status(201)
	                .body(new ApiResponseDTO<>("Category created", service.create(dto)));
	    }

    @Operation(summary = "Create Multiple categories")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/saveAll")
    public ResponseEntity<ApiResponseDTO<String>> createAll(
            @Valid @RequestBody List<CategoryRequestDTO> dto) {

        return ResponseEntity
                .status(201)
                .body(new ApiResponseDTO<>("Categories created", service.createAllCategories(dto
                )));
    }

    @Operation(summary = "Get all categories")
    @GetMapping
	    public ResponseEntity<ApiResponseDTO<List<CategoryResponseDTO>>> getAll() {

	        return ResponseEntity.ok(
	                new ApiResponseDTO<>("Categories fetched", service.getAll())
	        );
	    }

    @Operation(summary = "Get category by ID")
    @GetMapping("/{id}")
	    public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> getById(
	            @PathVariable Long id) {

	        return ResponseEntity.ok(
	                new ApiResponseDTO<>("Category fetched", service.getById(id))
	        );
	    }

    @Operation(summary = "Delete category by Id")
    @PreAuthorize("hasRole('ADMIN')")
	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponseDTO<String>> delete(@PathVariable Long id) {

	        service.delete(id);

	        return ResponseEntity.ok(
	                new ApiResponseDTO<>("Category deleted", null)
	        );
	    }
    @Operation(summary = "Delete All categories")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<ApiResponseDTO<String>> deleteAllCategories() {

        service.deleteAll();

        return ResponseEntity.ok(
                new ApiResponseDTO<>("All Categories deleted", null)
        );
    }

}
