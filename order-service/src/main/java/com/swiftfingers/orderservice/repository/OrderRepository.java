package com.swiftfingers.orderservice.repository;

import com.swiftfingers.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
