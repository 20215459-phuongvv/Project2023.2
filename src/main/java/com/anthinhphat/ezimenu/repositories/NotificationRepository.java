package com.anthinhphat.ezimenu.repositories;

import com.anthinhphat.ezimenu.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
