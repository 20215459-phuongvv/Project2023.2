package com.project.ezimenu.services.interfaces;

import com.project.ezimenu.dtos.OrderDTO.OrderResponseDTO;
import com.project.ezimenu.entities.Order;
import com.project.ezimenu.exceptions.NotFoundException;

import java.util.List;

public interface IOrderService {
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrderResponseById(Long orderId) throws NotFoundException;
    OrderResponseDTO getOrderResponseByTableId(Long tableId) throws NotFoundException;
    Order getOrderById(Long orderId) throws NotFoundException;
    Order sendOrder(Long tableId) throws NotFoundException;
    void deleteOrder(Long orderId) throws NotFoundException;
}