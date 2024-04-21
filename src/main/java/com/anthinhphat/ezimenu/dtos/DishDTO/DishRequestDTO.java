package com.anthinhphat.ezimenu.dtos.DishDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DishRequestDTO {
    @NotBlank
    private String dishName;
    @NotBlank
    private Long menuId;
    private String dishDescription;
    @NotBlank
    private int dishPrice;
    private String dishStatus;
}
