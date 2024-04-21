package com.anthinhphat.ezimenu.repositories;

import com.anthinhphat.ezimenu.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
