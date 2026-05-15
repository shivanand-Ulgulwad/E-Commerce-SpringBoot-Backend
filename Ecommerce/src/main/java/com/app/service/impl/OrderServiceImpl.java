package com.app.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.entity.*;
import com.app.exception.UnauthorizedAccessException;
import com.app.repository.OrderItemRepository;
import com.app.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.app.dto.OrderRequestDTO;
import com.app.dto.OrderResponseDTO;
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

    private final CurrentUserService currentUserService;


    private final OrderItemRepository orderItemRepo;

    @Override
    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestDTO dto) {

       User user = currentUserService.getCurrentUser();

        List<CartItem> cartItems = cartRepo.findByUserId(user.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 🔴 3. CREATE ORDER
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
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
        User currentUser = currentUserService.getCurrentUser();
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(currentUser.getId())) {

            throw new UnauthorizedAccessException(
                    "You cannot cancel this order"
            );
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {

            throw new RuntimeException("Order already cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);

        orderRepo.save(order);
    }

    @Override
    @Transactional
    public void cancelOrderItem(Long orderItemId) {

        User currentUser = currentUserService.getCurrentUser();

        OrderItem item = orderItemRepo.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        Order order = item.getOrder();

        if (!order.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedAccessException(
                    "You cannot cancel this order item"
            );
        }

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
    public Page<OrderResponseDTO> getMyOrders(int page,int size) {

        User user = currentUserService.getCurrentUser();

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("orderDate").descending()
        );

        Page<Order> orders =
                orderRepo.findByUserId(user.getId(), pageable);


        return orders.map(OrderMapper::toDTO);
    }



}
