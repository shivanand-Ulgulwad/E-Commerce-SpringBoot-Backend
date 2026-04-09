package com.app.controller;

import java.util.List;

import com.app.dto.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.service.ProductService;


import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/products")

public class ProductController {

	 @Autowired
	    private ProductService service;

	    @PostMapping
	    public ResponseEntity<ApiResponse<ProductResponseDTO>> create(
	           @Valid @RequestBody ProductRequestDTO dto) {

	        return ResponseEntity
	                .status(201)
	                .body(new ApiResponse<>("Product created", service.create(dto)));
	    }

    @PostMapping("/saveAll")
    public ResponseEntity<?> createAll(
            @Valid @RequestBody List<ProductRequestDTO> dto) {

        return ResponseEntity
                .status(201)
                .body(new ApiResponse<>("Product created", service.createAll(dto)));
    }

	    @GetMapping
	    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAll() {

	        return ResponseEntity.ok(
	                new ApiResponse<>("Products fetched", service.getAll())
	        );
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<ApiResponse<ProductResponseDTO>> getById(
	            @PathVariable Long id) {

	        return ResponseEntity.ok(
	                new ApiResponse<>("Product fetched", service.getById(id))
	        );
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {

	        service.delete(id);

	        return ResponseEntity.ok(
	                new ApiResponse<>("Product deleted", null)
	        );
	    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteAllProducts() {

        service.deleteAll();

        return ResponseEntity.ok(
                new ApiResponse<>("All products deleted", null)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> searchProducts(@RequestParam String query) {
        return ResponseEntity.ok(
                new ApiResponse<>("Product is fetched", service.searchProducts(query))
        );
    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<PageResponseDto<ProductResponseDTO>>> getProducts(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {

        PageRequestDto dto = new PageRequestDto();
        dto.setPage(page);
        dto.setSize(size);
        dto.setSortBy(sortBy);
        dto.setSortDir(sortDir);

        PageResponseDto<ProductResponseDTO> response =
                service.getProducts(query, dto);

        return ResponseEntity.ok(
                new ApiResponse<>("Products fetched with pagination", response)
        );
    }
}