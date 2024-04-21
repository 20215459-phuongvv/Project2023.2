package com.anthinhphat.ezimenu.repositories;

import com.anthinhphat.ezimenu.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByMenuTitle(String menuTitle);
}
