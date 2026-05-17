package com.app.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.dto.ApiResponseDTO;
import com.app.dto.CartRequestDTO;
import com.app.dto.CartResponseDTO;
import com.app.service.CartService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/cart")
@Tag(name = "Cart APIs", description = "Cart management APIs for adding and removing items")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class CartController {
	

	    private final CartService service;

    @Operation(
            summary = "Add item to cart",
            description = "Adds product to user cart or increments quantity if already exists"
    )
    @PostMapping
	    public ResponseEntity<ApiResponseDTO<CartResponseDTO>> addToCart(
	    		@Valid @RequestBody CartRequestDTO dto) {

	        return ResponseEntity
	                .status(201)
	                .body(new ApiResponseDTO<>("Item added to cart", service.addToCart(dto)));
	    }

    @Operation(summary = "Get cart")
	    @GetMapping("/{userId}")
	    public ResponseEntity<ApiResponseDTO<List<CartResponseDTO>>> getCart(
	            @PathVariable Long userId) {

	        return ResponseEntity.ok(
	                new ApiResponseDTO<>("Cart fetched", service.getCartByUser(userId))
	        );
	    }

    @PutMapping("/decrease/{id}")
    public ResponseEntity<ApiResponseDTO<String>> decrease(@PathVariable Long id) {
        service.decreaseQuantity(id);
        return ResponseEntity.ok(new ApiResponseDTO<>("Quantity decreased", null));
    }

	    // ✅ REMOVE ITEM
	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponseDTO<String>> remove(@PathVariable Long id) {

	        service.removeItem(id);

	        return ResponseEntity.ok(
	                new ApiResponseDTO<>("Item removed from cart", null)
	        );
	    }
}
