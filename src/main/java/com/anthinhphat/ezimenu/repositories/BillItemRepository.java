package com.anthinhphat.ezimenu.repositories;

import com.anthinhphat.ezimenu.entities.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillItemRepository extends JpaRepository<BillItem, Long> {
}
