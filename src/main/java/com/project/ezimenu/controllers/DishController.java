package com.project.ezimenu.controllers;

import com.project.ezimenu.dtos.DishDTO.DishRequestDTO;
import com.project.ezimenu.dtos.DishDTO.DishResponseDTO;
import com.project.ezimenu.entities.Dish;
import com.project.ezimenu.exceptions.BadRequestException;
import com.project.ezimenu.exceptions.NotFoundException;
import com.project.ezimenu.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DishController {
    @Autowired
    private DishService dishService;

    @GetMapping("/dishes")
    public ResponseEntity<?> getAllDishes(){
        List<DishResponseDTO> dishes = dishService.getAllDishes();
        if(dishes.isEmpty()){
            return new ResponseEntity<>("Hiện không có món ăn nào!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/dishes/{dishId}")
    public ResponseEntity<?> getDishById(@PathVariable Long dishId) throws NotFoundException {
        try{
            DishResponseDTO dish = dishService.getDishById(dishId);
            return ResponseEntity.ok(dish);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/admin/dishes", method = RequestMethod.POST)
    public ResponseEntity<?> addDish(@RequestBody DishRequestDTO dishRequestDTO) throws NotFoundException {
        try{
            Dish newDish = dishService.addDish(dishRequestDTO);
            return new ResponseEntity<>(newDish, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    @RequestMapping(path = "/admin/dishes/{dishId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDish(@PathVariable Long dishId,
                                        @RequestBody DishRequestDTO dishRequestDTO)
            throws NotFoundException {
        try{
            Dish updatedDish = dishService.updateDish(dishId, dishRequestDTO);
            return new ResponseEntity<>(updatedDish, HttpStatus.ACCEPTED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(path = "/admin/dishes/{dishId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteDish(@PathVariable Long dishId) throws NotFoundException {
        try{
            dishService.deleteDish(dishId);
            return new ResponseEntity<>("Xóa món ăn thành công!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
