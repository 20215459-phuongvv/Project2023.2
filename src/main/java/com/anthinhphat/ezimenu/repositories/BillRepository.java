package com.anthinhphat.ezimenu.repositories;

import com.anthinhphat.ezimenu.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
