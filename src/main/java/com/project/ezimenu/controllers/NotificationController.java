package com.project.ezimenu.controllers;

import com.project.ezimenu.dtos.NotificationDTO.NotificationResponseDTO;
import com.project.ezimenu.entities.Notification;
import com.project.ezimenu.exceptions.NotFoundException;
import com.project.ezimenu.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @RequestMapping(path = "/admin/notifications/{tableId}", method = RequestMethod.GET)
    public ResponseEntity<?> getTableNotifications(@PathVariable Long tableId) throws NotFoundException {
        try{
            List<NotificationResponseDTO> notifications = notificationService.getNotificationsByTableId(tableId);
            if(notifications.isEmpty()){
                return new ResponseEntity<>("Bàn này không có thông báo nào!", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(notifications);
        } catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/notifications/{tableId}")
    public ResponseEntity<?> addTableNotification(@PathVariable Long tableId,
                                                  @RequestBody String text)
            throws NotFoundException
    {
        try{
            Notification notification = notificationService.addNotification(tableId, text);
            return ResponseEntity.ok(notification);
        } catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path = "/admin/notifications/{notificationId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId)
            throws NotFoundException {
        try{
            notificationService.deleteNotification(notificationId);
            return ResponseEntity.ok("Đã xóa thông báo thành công");
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path = "/admin/notifications/table/{tableId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllNotifications(@PathVariable Long tableId)
            throws NotFoundException {
        try{
            notificationService.deleteAllTableNotifications(tableId);
            return ResponseEntity.ok("Đã xóa thông báo thành công");
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
