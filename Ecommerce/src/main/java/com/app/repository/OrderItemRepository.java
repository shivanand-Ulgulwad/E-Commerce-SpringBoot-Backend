package com.app.repository;

import com.app.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.Product;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
