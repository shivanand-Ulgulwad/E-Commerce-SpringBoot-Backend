package com.app.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
	private Long id;
    private String name;
    private double price;
    private String categoryName;
}
