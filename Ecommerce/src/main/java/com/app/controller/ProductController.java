package com.app.controller;

import java.util.List;

import com.app.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.app.service.ProductService;


import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/products")
@Tag(name = "Product APIs", description = "Product management, search, and catalog APIs")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ProductController {


	    private final ProductService service;

    @Operation(
            summary = "Create product",
            description = "ADMIN only endpoint to create a new product"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> create(
	           @Valid @RequestBody ProductRequestDTO dto) {

	        return ResponseEntity
	                .status(201)
	                .body(new ApiResponseDTO<>("Product created", service.create(dto)));
	    }

    @Operation(
            summary = "Create multiple products",
            description = "ADMIN only endpoint to create a new product"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<?> createAll(
            @Valid @RequestBody List<ProductRequestDTO> dto) {

        return ResponseEntity
                .status(201)
                .body(new ApiResponseDTO<>("Product created", service.createAll(dto)));
    }

    @Operation(
            summary = "Get All Products"
    )
    @GetMapping("/all")
	    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getAll() {

	        return ResponseEntity.ok(
	                new ApiResponseDTO<>("Products fetched", service.getAll())
	        );
	    }

    @Operation(
            summary = "Get product by ID",
            description = "Fetch product details using product ID"
    )
    @GetMapping("/{id}")
	    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> getById(
	            @PathVariable Long id) {

	        return ResponseEntity.ok(
	                new ApiResponseDTO<>("Product fetched", service.getById(id))
	        );
	    }

    @Operation(
            summary = "Delete product",
            description = "ADMIN only endpoint to delete product"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<ApiResponseDTO<String>> delete(@PathVariable Long id) {

	        service.delete(id);

	        return ResponseEntity.ok(
	                new ApiResponseDTO<>("Product deleted", null)
	        );
	    }

    @Operation(
            summary = "Delete All products",
            description = "ADMIN only endpoint to delete product"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<ApiResponseDTO<String>> deleteAllProducts() {

        service.deleteAll();

        return ResponseEntity.ok(
                new ApiResponseDTO<>("All products deleted", null)
        );
    }

    @Operation(
            summary = "Search products",
            description = "Search products by name or keyword"
    )
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> searchProducts(@RequestParam String query) {
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Product is fetched", service.searchProducts(query))
        );
    }

    @Operation(
            summary = "Get all products",
            description = "Supports pagination, sorting, and filtering"
    )
    @GetMapping
    public ResponseEntity<ApiResponseDTO<PageResponseDto<ProductResponseDTO>>> getProducts(
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
                new ApiResponseDTO<>("Products fetched with pagination", response)
        );
    }
}