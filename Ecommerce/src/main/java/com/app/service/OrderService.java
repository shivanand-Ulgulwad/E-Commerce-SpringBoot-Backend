package com.app.service;

import java.util.List;


import com.app.dto.OrderRequestDTO;
import com.app.dto.OrderResponseDTO;
import com.app.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
	 OrderResponseDTO placeOrder(OrderRequestDTO dto);
     void cancelOrder(Long id);

     void cancelOrderItem(Long orderItemId);

	    Page<OrderResponseDTO> getMyOrders(int page,
                                           int size);
}
