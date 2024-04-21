package com.project.ezimenu.repositories;

import com.project.ezimenu.entities.Order;
import com.project.ezimenu.entities.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByTable(Table table);
}
