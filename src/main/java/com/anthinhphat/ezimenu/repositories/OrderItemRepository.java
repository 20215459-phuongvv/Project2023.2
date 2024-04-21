package com.anthinhphat.ezimenu.repositories;

import com.anthinhphat.ezimenu.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
