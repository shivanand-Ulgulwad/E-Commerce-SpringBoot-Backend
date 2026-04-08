package com.app.dto;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Long id;
    private String productName;
    private int quantity;
    private double price;
}
