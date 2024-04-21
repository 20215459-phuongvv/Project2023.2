package com.anthinhphat.ezimenu.dtos.DishDTO;

import lombok.Data;

@Data
public class DishResponseDTO {
    private long dishId;
    private String dishName;
    private String dishDescription;
    private int dishPrice;
    private String dishStatus;
}
