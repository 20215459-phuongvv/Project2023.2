package com.anthinhphat.ezimenu.services.interfaces;

import com.anthinhphat.ezimenu.dtos.OrderDTO.OrderResponseDTO;
import com.anthinhphat.ezimenu.entities.Order;
import com.anthinhphat.ezimenu.exceptions.NotFoundException;

import java.util.List;

public interface IOrderService {
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrderResponseById(Long orderId) throws NotFoundException;
    OrderResponseDTO getOrderResponseByTableId(Long tableId) throws NotFoundException;
    Order getOrderById(Long orderId) throws NotFoundException;
    Order sendOrder(Long tableId) throws NotFoundException;
    void deleteOrder(Long orderId) throws NotFoundException;
}