package com.anthinhphat.ezimenu.dtos.MenuDTO;
import com.anthinhphat.ezimenu.dtos.DishDTO.DishRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class MenuRequestDTO {
    @NotBlank(message = "Tên danh mục không được để trống!")
    private String menuTitle;
    private String menuDescription;
    private List<DishRequestDTO> dishRequestDTO;
}
