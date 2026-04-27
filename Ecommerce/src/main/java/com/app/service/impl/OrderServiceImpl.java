package com.app.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import com.app.dto.OrderRequestDTO;
import com.app.dto.OrderResponseDTO;
import com.app.entity.CartItem;
import com.app.entity.Category;
import com.app.entity.Order;
import com.app.entity.OrderItem;
import com.app.entity.User;
import com.app.mapper.CategoryMapper;
import com.app.mapper.OrderMapper;
import com.app.repository.CartItemRepository;
import com.app.repository.OrderRepository;
import com.app.repository.UserRepository;
import com.app.service.OrderService;

import jakarta.transaction.Transactional;

@Service("orderService")
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	

    private final CartItemRepository cartRepo;


    private final OrderRepository orderRepo;


    private final UserRepository userRepo;


    private final OrderItemRepository orderItemRepo;

    @Override
    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestDTO dto) {

        // 🔴 1. VALIDATE USER
        if (dto.getUserId() == null) {
            throw new RuntimeException("UserId is required");
        }

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔴 2. GET CART ITEMS
        List<CartItem> cartItems = cartRepo.findByUserId(user.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 🔴 3. CREATE ORDER
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());


        Map<Long, OrderItem> itemMap = new HashMap<>();


        // 🔴 4. CONVERT CART → ORDER ITEMS
        for (CartItem cartItem : cartItems) {

            Long productId = cartItem.getProduct().getId();

            if (itemMap.containsKey(productId)) {

                // ✅ already exists → increase quantity
                OrderItem existing = itemMap.get(productId);
                existing.setQuantity(
                        existing.getQuantity() + cartItem.getQuantity()
                );

            } else {

                // ✅ new item
                OrderItem item = new OrderItem();
                item.setProduct(cartItem.getProduct());
                item.setQuantity(cartItem.getQuantity());
                item.setPrice(cartItem.getProduct().getPrice());
                item.setOrder(order);

                itemMap.put(productId, item);
            }
        }
        List<OrderItem> orderItems = new ArrayList<>(itemMap.values());
        // ✅ calculate total
        double total = orderItems.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        order.setItems(orderItems);
        order.setTotalAmount(total);

        // 🔴 5. SAVE ORDER
        Order saved = orderRepo.save(order);

        // 🔴 6. CLEAR CART
        cartRepo.deleteAll(cartItems);

        // 🔴 7. RETURN DTO
        return OrderMapper.toDTO(saved);
    }

    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if ("CANCELLED".equals(order.getStatus())) {
            throw new RuntimeException("Order already cancelled");
        }

        order.setStatus("CANCELLED");

        orderRepo.save(order);
    }

    @Override
    @Transactional
    public void cancelOrderItem(Long orderItemId) {
        OrderItem item = orderItemRepo.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        Order order = item.getOrder();

        // 🔴 1. Remove item from order
        order.getItems().remove(item);

        // 🔴 2. Delete item from DB
        orderItemRepo.delete(item);

        // 🔴 3. Recalculate order total
        double newTotal = order.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        order.setTotalAmount(newTotal);

        // 🔴 4. Save order with updated total
        orderRepo.save(order);
    }

    // ✅ GET ORDERS BY USER
    @Override
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {

        List<Order> orders = orderRepo.findByUserId(userId);

        return orders.stream()
                .map(OrderMapper::toDTO)
                .toList();
    }



}
