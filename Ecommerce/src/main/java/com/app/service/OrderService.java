package com.app.service;

import java.util.List;


import com.app.dto.OrderRequestDTO;
import com.app.dto.OrderResponseDTO;
import com.app.entity.Order;

public interface OrderService {
	 OrderResponseDTO placeOrder(OrderRequestDTO dto);
     void cancelOrder(Long id);

     void cancelOrderItem(Long orderItemId);

	    List<OrderResponseDTO> getOrdersByUser(Long userId);
}
