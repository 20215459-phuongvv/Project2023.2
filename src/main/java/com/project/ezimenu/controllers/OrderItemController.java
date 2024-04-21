package com.project.ezimenu.controllers;

import com.project.ezimenu.dtos.OrderItemDTO.OrderItemRequestDTO;
import com.project.ezimenu.entities.Message;
import com.project.ezimenu.entities.Order;
import com.project.ezimenu.entities.OrderItem;
import com.project.ezimenu.exceptions.BadRequestException;
import com.project.ezimenu.exceptions.NotFoundException;
import com.project.ezimenu.services.NotificationService;
import com.project.ezimenu.services.OrderItemService;
import com.project.ezimenu.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class OrderItemController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private MessageController messageController;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    @PostMapping("orders/{orderId}/items")
    public ResponseEntity<?> addOrderItem(@PathVariable Long orderId,
                                          @RequestBody OrderItemRequestDTO orderItemRequestDTO)
            throws NotFoundException, BadRequestException {
        try{
            OrderItem createdOrderItem = orderItemService.addOrderItem(orderId, orderItemRequestDTO);
            return new ResponseEntity<>(createdOrderItem, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("orders/{orderId}/items/{orderItemId}")
    public ResponseEntity<?> updateOrderItem(@PathVariable Long orderId,
                                             @PathVariable Long orderItemId,
                                             @RequestBody OrderItemRequestDTO orderItemRequestDTO)
            throws NotFoundException, BadRequestException {
        try{
            Order updatedOrder = orderItemService.updateOrderItem(orderId, orderItemId, orderItemRequestDTO);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path = "admin/orders/{orderId}/items/{orderItemId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOrderItemForAdmin(@PathVariable Long orderId,
                                                     @PathVariable Long orderItemId,
                                                     @RequestBody OrderItemRequestDTO orderItemRequestDTO)
            throws NotFoundException {
        try{
            Order updatedOrder = orderItemService.updateOrderItemForAdmin(orderId, orderItemId, orderItemRequestDTO);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path = "admin/orders/{orderId}/items/{orderItemId}/status", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOrderItemStatus(@PathVariable Long orderId,
                                                   @PathVariable Long orderItemId)
            throws NotFoundException {
        try{
            Order updatedOrder = orderItemService.updateOrderItemStatus(orderId, orderItemId);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("orders/{orderId}/items/{orderItemId}/request")
    public ResponseEntity<?> sendOrderItemRequest(@PathVariable Long orderId,
                                                  @PathVariable Long orderItemId)
            throws NotFoundException {
        try{
            Order order = orderService.getOrderById(orderId);
            OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
            if(orderItem.getOrder().getOrderId() != order.getOrderId()){
                return new ResponseEntity<>("Mo này không nằm trong order " + orderId + "!", HttpStatus.NOT_FOUND);
            }
            if(!orderItem.getDishStatus().equals("Đang ra món")){
                return new ResponseEntity<>("Món này đã ra rồi!", HttpStatus.OK);
            }
            notificationService.addNotification(order.getTable().getTableId(), "Khách ở bàn " + order.getTable().getTableName() + " đang yêu cầu món " + orderItem.getDish().getDishName() + " lên trước.");
            Message request = new Message();
            request.setTo("admin");
            request.setText(LocalDateTime.now().format(formatter) + ": Khách ở bàn " + order.getTable().getTableName() + " đang yêu cầu món " + orderItem.getDish().getDishName() + " lên trước.");
            messageController.sendToSpecificUser(request);
            return new ResponseEntity<>("Yêu cầu đã được gửi đi", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("orders/{orderId}/items/{orderItemId}")
    public ResponseEntity<?> deleteOrderItem(@PathVariable Long orderId,
                                             @PathVariable Long orderItemId)
            throws NotFoundException, BadRequestException {
        try{
            Order updatedOrder = orderItemService.deleteOrderItem(orderId, orderItemId);
            return ResponseEntity.ok(updatedOrder);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
