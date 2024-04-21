package com.project.ezimenu.repositories;

import com.project.ezimenu.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByMenuTitle(String menuTitle);
}
