package com.project.ezimenu.services.interfaces;

import com.project.ezimenu.dtos.DishDTO.DishRequestDTO;
import com.project.ezimenu.dtos.DishDTO.DishResponseDTO;
import com.project.ezimenu.entities.Dish;
import com.project.ezimenu.exceptions.BadRequestException;
import com.project.ezimenu.exceptions.NotFoundException;

import java.util.List;

public interface IDishService {
    Dish addDish(DishRequestDTO dishRequestDTO) throws NotFoundException, BadRequestException;
    DishResponseDTO getDishById(Long dishId) throws NotFoundException;
    List<DishResponseDTO> getAllDishes();
    Dish updateDish(Long dishId, DishRequestDTO dishRequestDTO) throws NotFoundException, BadRequestException;
    void deleteDish(Long dishId) throws NotFoundException;
}
