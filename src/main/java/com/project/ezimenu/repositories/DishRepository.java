package com.project.ezimenu.repositories;

import com.project.ezimenu.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
