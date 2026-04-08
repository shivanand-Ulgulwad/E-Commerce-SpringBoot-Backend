package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.dto.ApiResponse;
import com.app.dto.CartRequestDTO;
import com.app.dto.CartResponseDTO;
import com.app.service.CartService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/cart")

public class CartController {
	
	 @Autowired
	    private CartService service;

	    // ✅ ADD TO CART
	    @PostMapping("/save")
	    public ResponseEntity<ApiResponse<CartResponseDTO>> addToCart(
	    		@Valid @RequestBody CartRequestDTO dto) {

	        return ResponseEntity
	                .status(201)
	                .body(new ApiResponse<>("Item added to cart", service.addToCart(dto)));
	    }

	    // ✅ GET USER CART
	    @GetMapping("/{userId}")
	    public ResponseEntity<ApiResponse<List<CartResponseDTO>>> getCart(
	            @PathVariable Long userId) {

	        return ResponseEntity.ok(
	                new ApiResponse<>("Cart fetched", service.getCartByUser(userId))
	        );
	    }

    @PutMapping("/decrease/{id}")
    public void decrease(@PathVariable Long id) {
        service.decreaseQuantity(id);
    }

	    // ✅ REMOVE ITEM
	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse<String>> remove(@PathVariable Long id) {

	        service.removeItem(id);

	        return ResponseEntity.ok(
	                new ApiResponse<>("Item removed from cart", null)
	        );
	    }
}
