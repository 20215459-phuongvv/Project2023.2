package com.anthinhphat.ezimenu.dtos.OrderDTO;

import com.anthinhphat.ezimenu.dtos.OrderItemDTO.OrderItemRequestDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequestDTO {
    private long tableId;
    private LocalDateTime orderTime;
    private List<OrderItemRequestDTO> orderItemRequestDTO;
}
