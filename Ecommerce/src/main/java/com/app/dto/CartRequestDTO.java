package com.app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartRequestDTO {
	
	 @NotBlank(message = "Product name is required")
	    private String name;

	    @Positive(message = "Price must be greater than 0")
	    private double price;

	    @NotNull(message = "Category ID is required")
	    private Long categoryId;
	    
	    @NotNull(message = "User ID is required")
	    private Long userId;

	    @NotNull(message = "Product ID is required")
	    private Long productId;

	    @Min(value = 1, message = "Quantity must be at least 1")
	    private int quantity;
}
