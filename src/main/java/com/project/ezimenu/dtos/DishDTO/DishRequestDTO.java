package com.project.ezimenu.dtos.DishDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DishRequestDTO {
    @NotBlank
    private String dishName;
    @NotBlank
    private Long menuId;
    private String dishDescription;
    @NotBlank
    private Integer dishPrice;
    private Short dishStatus;
    private MultipartFile thumbnail;
}
