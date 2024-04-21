package com.anthinhphat.ezimenu.repositories;

import com.anthinhphat.ezimenu.entities.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TableRepository extends JpaRepository<Table, Long> {
    Optional<List<Table>> findByTableStatus(String status);

    Optional<List<Table>> findByTableStatusIgnoreCase(String status);

    Optional<Table> findByTableName(String tableName);
}
