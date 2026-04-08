package com.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductRequestDTO {
	 @NotBlank(message = "Product name is required")
	    private String name;

	    @Positive(message = "Price must be greater than 0")
	    private double price;

	    @NotNull(message = "Category ID is required")
	    private Long categoryId;
    
}
