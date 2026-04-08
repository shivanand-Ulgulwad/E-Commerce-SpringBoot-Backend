package com.app.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponseDTO {

	 private Long id;
	    private LocalDateTime orderDate;
	    private double totalAmount;
    private String status;
	    private List<OrderItemResponseDTO> items;
}
