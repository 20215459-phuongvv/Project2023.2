package com.anthinhphat.ezimenu.controllers;

import com.anthinhphat.ezimenu.dtos.OrderDTO.OrderResponseDTO;
import com.anthinhphat.ezimenu.entities.Message;
import com.anthinhphat.ezimenu.entities.Order;
import com.anthinhphat.ezimenu.exceptions.NotFoundException;
import com.anthinhphat.ezimenu.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private MessageController messageController;
    @Autowired
    private OrderService orderService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    @RequestMapping(path = "admin/orders", method = RequestMethod.GET)
    public ResponseEntity<?> getAllOrders(){
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        if(orders.isEmpty()){
            return new ResponseEntity<>("Hiện không có order nào!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/admin/orders/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) throws NotFoundException {
        try{
            OrderResponseDTO order = orderService.getOrderResponseById(orderId);
            return ResponseEntity.ok(order);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/admin/orders/table/{tableId}")
    public ResponseEntity<?> getOrderByTableId(@PathVariable Long tableId) throws NotFoundException {
        try{
            OrderResponseDTO order = orderService.getOrderResponseByTableId(tableId);
            return ResponseEntity.ok(order);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path = "orders/{tableId}", method = RequestMethod.POST)
    public ResponseEntity<?> sendOrder(@PathVariable Long tableId){
        try{
            LocalDateTime currentDateTime = LocalDateTime.now();
            Order newOrder = orderService.sendOrder(tableId);
            Message message = new Message();
            message.setText(currentDateTime.format(formatter) + ": " + "Bàn " + tableId + " đã tạo order mới!");
            message.setTo("admin");
            messageController.sendToSpecificUser(message);
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "admin/orders/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId)
            throws NotFoundException {
        try{
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok("Đã xóa đơn hàng thành công!");
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
