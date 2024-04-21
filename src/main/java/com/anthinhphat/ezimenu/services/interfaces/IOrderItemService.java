package com.anthinhphat.ezimenu.services.interfaces;

import com.anthinhphat.ezimenu.dtos.OrderItemDTO.OrderItemRequestDTO;
import com.anthinhphat.ezimenu.entities.Order;
import com.anthinhphat.ezimenu.entities.OrderItem;
import com.anthinhphat.ezimenu.exceptions.BadRequestException;
import com.anthinhphat.ezimenu.exceptions.NotFoundException;

public interface IOrderItemService {
    OrderItem addOrderItem(Long orderId, OrderItemRequestDTO orderItemRequestDTO) throws NotFoundException, BadRequestException;
    Order updateOrderItem(Long orderId, Long orderItemId, OrderItemRequestDTO orderItemRequestDTO) throws NotFoundException, BadRequestException;
    Order updateOrderItemForAdmin(Long orderId, Long orderItemId, OrderItemRequestDTO orderItemRequestDTO) throws NotFoundException;
    Order updateOrderItemStatus(Long orderId, Long orderItemId) throws NotFoundException;
    Order deleteOrderItem(Long orderId, Long orderItemId) throws NotFoundException, BadRequestException;
    OrderItem getOrderItemById(Long orderItemId) throws NotFoundException;
}
