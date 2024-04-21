package com.anthinhphat.ezimenu.repositories;

import com.anthinhphat.ezimenu.entities.Order;
import com.anthinhphat.ezimenu.entities.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByTable(Table table);
}
