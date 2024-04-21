package com.anthinhphat.ezimenu.services.interfaces;

import com.anthinhphat.ezimenu.dtos.DishDTO.DishRequestDTO;
import com.anthinhphat.ezimenu.dtos.DishDTO.DishResponseDTO;
import com.anthinhphat.ezimenu.entities.Dish;
import com.anthinhphat.ezimenu.exceptions.BadRequestException;
import com.anthinhphat.ezimenu.exceptions.NotFoundException;

import java.util.List;

public interface IDishService {
    Dish addDish(DishRequestDTO dishRequestDTO) throws NotFoundException, BadRequestException;
    DishResponseDTO getDishById(Long dishId) throws NotFoundException;
    List<DishResponseDTO> getAllDishes();
    Dish updateDish(Long dishId, DishRequestDTO dishRequestDTO) throws NotFoundException, BadRequestException;
    void deleteDish(Long dishId) throws NotFoundException;
}
