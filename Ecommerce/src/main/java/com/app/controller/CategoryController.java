package com.app.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.dto.ApiResponse;
import com.app.dto.CategoryRequestDTO;
import com.app.dto.CategoryResponseDTO;
import com.app.service.CategoryService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
	

	    private final CategoryService service;

	    @PostMapping
	    public ResponseEntity<ApiResponse<CategoryResponseDTO>> create(
	    		@Valid @RequestBody CategoryRequestDTO dto) {

	        return ResponseEntity
	                .status(201)
	                .body(new ApiResponse<>("Category created", service.create(dto)));
	    }

    @PostMapping("/saveAll")
    public ResponseEntity<ApiResponse<String>> createAll(
            @Valid @RequestBody List<CategoryRequestDTO> dto) {

        return ResponseEntity
                .status(201)
                .body(new ApiResponse<>("Categories created", service.createAllCategories(dto
                )));
    }

	    @GetMapping()
	    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getAll() {

	        return ResponseEntity.ok(
	                new ApiResponse<>("Categories fetched", service.getAll())
	        );
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getById(
	            @PathVariable Long id) {

	        return ResponseEntity.ok(
	                new ApiResponse<>("Category fetched", service.getById(id))
	        );
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {

	        service.delete(id);

	        return ResponseEntity.ok(
	                new ApiResponse<>("Category deleted", null)
	        );
	    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteAllCategories() {

        service.deleteAll();

        return ResponseEntity.ok(
                new ApiResponse<>("All Categories deleted", null)
        );
    }

}
