package com.anthinhphat.ezimenu.controllers;

import com.anthinhphat.ezimenu.dtos.MenuDTO.MenuRequestDTO;
import com.anthinhphat.ezimenu.dtos.MenuDTO.MenuResponseDTO;
import com.anthinhphat.ezimenu.entities.Menu;
import com.anthinhphat.ezimenu.exceptions.BadRequestException;
import com.anthinhphat.ezimenu.exceptions.NotFoundException;
import com.anthinhphat.ezimenu.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping("/menus")
    public ResponseEntity<?> getAllMenus() throws NotFoundException {
        List<MenuResponseDTO> menus = menuService.getAllMenus();
        if(menus.isEmpty()){
            return new ResponseEntity<>("Hiện không có menu nào!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(menus);
    }
    @GetMapping("/{tableId}/menus")
    public ResponseEntity<?> getAllMenusAndCreateOrder(@PathVariable Long tableId) {
        try{
            List<MenuResponseDTO> menus = menuService.getAllMenusAndCreateOrder(tableId);
            if(menus.isEmpty()){
                return new ResponseEntity<>("Hiện không có menu nào!", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(menus);
        } catch(NotFoundException e){
            return new ResponseEntity<>("Bàn này không tồn tại!", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{tableId}/menus/{menuId}")
    public ResponseEntity<?> getMenuById(@PathVariable Long menuId) throws NotFoundException {
        try {
            MenuResponseDTO menu = menuService.getMenuById(menuId);
            return ResponseEntity.ok(menu);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path = "/admin/menus", method = RequestMethod.POST)
    public ResponseEntity<?> addMenu(@RequestBody MenuRequestDTO menuRequestDTO){
        try{
            Menu newMenu = menuService.addMenu(menuRequestDTO);
            return new ResponseEntity<>(newMenu, HttpStatus.CREATED);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(path = "/admin/menus/{menuId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMenu(@PathVariable Long menuId,
                                        @RequestBody MenuRequestDTO menuRequestDTO)
            throws NotFoundException, BadRequestException {
        try{
            Menu updatedMenu = menuService.updateMenu(menuId, menuRequestDTO);
            return new ResponseEntity<>(updatedMenu, HttpStatus.ACCEPTED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(path = "/admin/menus/{menuId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMenu(@PathVariable Long menuId) throws NotFoundException {
        try{
            menuService.deleteMenu(menuId);
            return new ResponseEntity<>("Đã xóa menu thành công!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
