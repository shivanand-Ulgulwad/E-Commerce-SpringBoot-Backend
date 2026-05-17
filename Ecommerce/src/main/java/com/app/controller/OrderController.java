package com.app.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.dto.ApiResponseDTO;
import com.app.dto.OrderRequestDTO;
import com.app.dto.OrderResponseDTO;
import com.app.service.OrderService;


@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/orders")
@Tag(name = "Order APIs", description = "Order management, checkout, and order history APIs")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class OrderController {
		

	    private final OrderService service;

    @Operation(
            summary = "Place order",
            description = "Converts cart items into a confirmed order"
    )
    @PostMapping
	    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> placeOrder(
	            @RequestBody OrderRequestDTO dto) {

	        return ResponseEntity
	                .status(201)
	                .body(new ApiResponseDTO<>("Order placed", service.placeOrder(dto)));
	    }

    @Operation(summary = "Get user orders")
    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponseDTO<Page<OrderResponseDTO>>> getMyOrders(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(
                new ApiResponseDTO<>(
                        "Orders fetched successfully",
                        service.getMyOrders(page, size)
                )
        );
    }


    @Operation(summary = "Cancel order")
    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        service.cancelOrder(id);
        return ResponseEntity.ok("Order cancelled");
    }

    @Operation(summary = "Cancel order item")
    @DeleteMapping("/items/{orderItemId}")
    public ResponseEntity<String> cancelOrderItem(@PathVariable Long orderItemId) {
        service.cancelOrderItem(orderItemId);
        return ResponseEntity.ok("Order item cancelled successfully");
    }
}
