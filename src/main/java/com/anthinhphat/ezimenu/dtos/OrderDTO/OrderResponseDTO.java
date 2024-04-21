package com.anthinhphat.ezimenu.dtos.OrderDTO;

import com.anthinhphat.ezimenu.dtos.OrderItemDTO.OrderItemResponseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private String tableName;
    private LocalDateTime orderTime;
    private List<OrderItemResponseDTO> orderItemResponseDTO;
}
