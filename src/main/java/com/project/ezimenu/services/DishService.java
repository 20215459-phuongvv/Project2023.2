package com.project.ezimenu.services;

import com.project.ezimenu.dtos.DishDTO.DishRequestDTO;
import com.project.ezimenu.dtos.DishDTO.DishResponseDTO;
import com.project.ezimenu.entities.Dish;
import com.project.ezimenu.entities.Menu;
import com.project.ezimenu.exceptions.BadRequestException;
import com.project.ezimenu.exceptions.NotFoundException;
import com.project.ezimenu.repositories.DishRepository;
import com.project.ezimenu.repositories.MenuRepository;
import com.project.ezimenu.services.interfaces.IDishService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DishService implements IDishService {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ModelMapper modelMapper;
    public Dish addDish(DishRequestDTO dishRequestDTO) throws NotFoundException, BadRequestException {
        if(dishRequestDTO.getDishName() == null || "".equals(dishRequestDTO.getDishName()) || Objects.isNull(dishRequestDTO.getDishPrice()) || Objects.isNull(dishRequestDTO.getMenuId())){
            throw new BadRequestException("Vui lòng nhập đầy đủ thông tin!");
        }
        Menu menu = menuRepository.findById(dishRequestDTO.getMenuId())
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy danh mục có id: " + dishRequestDTO.getMenuId()));
        Dish newDish = new Dish();
        newDish.setMenu(menu);
        newDish.setDishName(dishRequestDTO.getDishName());
        newDish.setDishPrice(dishRequestDTO.getDishPrice());
        if(dishRequestDTO.getDishDescription() != null) newDish.setDishDescription(dishRequestDTO.getDishDescription());
        newDish.setDishStatus("Còn món");
        menu.getDishes().add(newDish);
        return dishRepository.save(newDish);
    }
    public DishResponseDTO getDishById(Long dishId) throws NotFoundException {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy món ăn có id: " + dishId));
        return modelMapper.map(dish, DishResponseDTO.class);
    }

    public List<DishResponseDTO> getAllDishes() {
        List<Dish> dishes = dishRepository.findAll();
        return dishes.stream()
                .map(dish -> modelMapper.map(dish, DishResponseDTO.class))
                .collect(Collectors.toList());
    }
    public Dish updateDish(Long dishId, DishRequestDTO dishRequestDTO) throws NotFoundException, BadRequestException {
        if(dishRequestDTO.getDishName() == null || "".equals(dishRequestDTO.getDishName()) || Objects.isNull(dishRequestDTO.getDishPrice()) || Objects.isNull(dishRequestDTO.getMenuId())){
            throw new BadRequestException("Vui lòng nhập đầy đủ thông tin!");
        }
        Dish updatedDish = dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy món ăn có id: " + dishId));
        Menu menu = menuRepository.findById(dishRequestDTO.getMenuId())
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy danh mục có id: " + dishRequestDTO.getMenuId()));
        updatedDish.setMenu(menu);
        updatedDish.setDishName(dishRequestDTO.getDishName());
        updatedDish.setDishDescription(dishRequestDTO.getDishDescription());
        updatedDish.setDishPrice(dishRequestDTO.getDishPrice());
        updatedDish.setDishStatus(dishRequestDTO.getDishStatus());
        return dishRepository.save(updatedDish);
    }

    public void deleteDish(Long dishId) throws NotFoundException {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy món ăn có id: " + dishId));
        dishRepository.deleteById(dishId);
    }
}
