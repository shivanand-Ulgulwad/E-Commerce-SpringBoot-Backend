package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.CartItem;
import com.app.entity.Order;
import com.app.entity.Product;

public interface OrderRepository extends JpaRepository<Order, Long> {
	 Page<Order> findByUserId(Long userId, Pageable pageable);
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}
