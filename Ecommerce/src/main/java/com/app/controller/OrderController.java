package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.dto.ApiResponse;
import com.app.dto.OrderRequestDTO;
import com.app.dto.OrderResponseDTO;
import com.app.service.OrderService;


@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/orders")
public class OrderController {
		
	 @Autowired
	    private OrderService service;

	    @PostMapping
	    public ResponseEntity<ApiResponse<OrderResponseDTO>> placeOrder(
	            @RequestBody OrderRequestDTO dto) {

	        return ResponseEntity
	                .status(201)
	                .body(new ApiResponse<>("Order placed", service.placeOrder(dto)));
	    }

	    @GetMapping("/user/{userId}")
	    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getOrders(
	            @PathVariable Long userId) {

	        return ResponseEntity.ok(
	                new ApiResponse<>("Orders fetched", service.getOrdersByUser(userId))
	        );
	    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        service.cancelOrder(id);
        return ResponseEntity.ok("Order cancelled");
    }

    @DeleteMapping("/items/{orderItemId}")
    public ResponseEntity<String> cancelOrderItem(@PathVariable Long orderItemId) {
        service.cancelOrderItem(orderItemId);
        return ResponseEntity.ok("Order item cancelled successfully");
    }
}
