package com.anthinhphat.ezimenu.services.interfaces;

import com.anthinhphat.ezimenu.dtos.MenuDTO.MenuRequestDTO;
import com.anthinhphat.ezimenu.dtos.MenuDTO.MenuResponseDTO;
import com.anthinhphat.ezimenu.entities.Menu;
import com.anthinhphat.ezimenu.exceptions.BadRequestException;
import com.anthinhphat.ezimenu.exceptions.NotFoundException;

import java.util.List;

public interface IMenuService {
    List<MenuResponseDTO> getAllMenus();
    List<MenuResponseDTO> getAllMenusAndCreateOrder(Long tableId) throws NotFoundException;
    MenuResponseDTO getMenuById(Long menuId) throws NotFoundException;
    Menu addMenu(MenuRequestDTO menuRequestDTO) throws BadRequestException;
    Menu updateMenu(Long menuId, MenuRequestDTO menuRequestDTO) throws NotFoundException, BadRequestException;
    void deleteMenu(Long menuId) throws NotFoundException;
}
