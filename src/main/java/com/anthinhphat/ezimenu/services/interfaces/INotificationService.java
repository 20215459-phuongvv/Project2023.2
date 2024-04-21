package com.anthinhphat.ezimenu.services.interfaces;

import com.anthinhphat.ezimenu.dtos.NotificationDTO.NotificationResponseDTO;
import com.anthinhphat.ezimenu.entities.Notification;
import com.anthinhphat.ezimenu.exceptions.NotFoundException;

import java.util.List;

public interface INotificationService {
    List<NotificationResponseDTO> getNotificationsByTableId(Long tableId) throws NotFoundException;
    Notification addNotification(Long tableId, String text) throws NotFoundException;
    void deleteNotification(Long notificationId) throws NotFoundException;
    void deleteAllTableNotifications(Long tableId) throws NotFoundException;
}
