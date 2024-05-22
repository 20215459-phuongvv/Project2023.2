package com.project.ezimenu.services;

import com.project.ezimenu.dtos.DishDTO.DishRequestDTO;
import com.project.ezimenu.dtos.DishDTO.DishResponseDTO;
import com.project.ezimenu.dtos.MenuDTO.MenuRequestDTO;
import com.project.ezimenu.dtos.MenuDTO.MenuResponseDTO;
import com.project.ezimenu.entities.Dish;
import com.project.ezimenu.entities.Menu;
import com.project.ezimenu.entities.Order;
import com.project.ezimenu.entities.Table;
import com.project.ezimenu.exceptions.BadRequestException;
import com.project.ezimenu.exceptions.NotFoundException;
import com.project.ezimenu.repositories.DishRepository;
import com.project.ezimenu.repositories.MenuRepository;
import com.project.ezimenu.repositories.OrderRepository;
import com.project.ezimenu.repositories.TableRepository;
import com.project.ezimenu.services.interfaces.IMenuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MenuService implements IMenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Type listType;
    public List<MenuResponseDTO> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(menu -> {
                    MenuResponseDTO menuResponseDTO = modelMapper.map(menu, MenuResponseDTO.class);
                    List<DishResponseDTO> dishResponseDTOs = menu.getDishes().stream()
                            .map(dish -> modelMapper.map(dish, DishResponseDTO.class))
                            .collect(Collectors.toList());
                    menuResponseDTO.setDishResponseDTO(dishResponseDTOs);
                    return menuResponseDTO;
                })
                .collect(Collectors.toList());
    }
    public List<MenuResponseDTO> getAllMenusAndCreateOrder(Long tableId) throws NotFoundException {
        Table table = tableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy bàn với id: " + tableId));
        // Tạo order mới nếu bàn trống
        if(table.getTableStatus().equals("Đang trống")){
            table.setTableStatus("Đang order");
            table.setStartOrderingTime(LocalDateTime.now());
            Order newOrder = new Order();
            newOrder.setTable(table);
            newOrder.setOrderStatus("Đang order");
            tableRepository.save(table);
            orderRepository.save(newOrder);
        }
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(menu -> {
                    MenuResponseDTO menuResponseDTO = modelMapper.map(menu, MenuResponseDTO.class);
                    List<DishResponseDTO> dishResponseDTOs = menu.getDishes().stream()
                            .map(dish -> modelMapper.map(dish, DishResponseDTO.class))
                            .collect(Collectors.toList());
                    menuResponseDTO.setDishResponseDTO(dishResponseDTOs);
                    return menuResponseDTO;
                })
                .collect(Collectors.toList());
    }

    public MenuResponseDTO getMenuById(Long menuId) throws NotFoundException{
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy danh mục có id: " + menuId));
        MenuResponseDTO menuResponseDTO = modelMapper.map(menu, MenuResponseDTO.class);
        List<DishResponseDTO> dishResponseDTOs = menu.getDishes().stream()
                .map(dish -> modelMapper.map(dish, DishResponseDTO.class))
                .collect(Collectors.toList());
        menuResponseDTO.setDishResponseDTO(dishResponseDTOs);
        return menuResponseDTO;
    }

    public Menu addMenu(MenuRequestDTO menuRequestDTO) throws BadRequestException {
        if(menuRequestDTO.getMenuTitle() == null || "".equals(menuRequestDTO.getMenuTitle())){
            throw new BadRequestException("Vui lòng điền tên danh mục!");
        }
        Optional<Menu> existingMenu = menuRepository.findByMenuTitle(menuRequestDTO.getMenuTitle());
        if(existingMenu.isPresent()){
            throw new BadRequestException("Danh mục này đã tồn tại!");
        }
        Menu newMenu = new Menu();
        newMenu.setMenuTitle(menuRequestDTO.getMenuTitle());
        newMenu.setMenuDescription(menuRequestDTO.getMenuDescription());
        newMenu = menuRepository.save(newMenu);
        if(menuRequestDTO.getDishRequestDTO() != null && !menuRequestDTO.getDishRequestDTO().isEmpty()){
            Menu finalNewMenu = newMenu;
            List<Dish> dishes = menuRequestDTO.getDishRequestDTO()
                    .stream()
                    .map(dishRequestDTO -> {
                        Dish dish = convertToDish(dishRequestDTO);
                        dish.setMenu(finalNewMenu);
                        return dish;
                    })
                    .collect(Collectors.toList());
            newMenu.setDishes(dishes);
            dishRepository.saveAll(dishes);
        }
        return menuRepository.save(newMenu);
    }

    public Menu updateMenu(Long menuId, MenuRequestDTO menuRequestDTO) throws NotFoundException, BadRequestException {
        if(menuRequestDTO.getMenuTitle() == null || "".equals(menuRequestDTO.getMenuTitle())){
            throw new BadRequestException("Vui lòng điền tên danh mục!");
        }
        Menu updatedMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy danh mục có id: " + menuId));
        updatedMenu.setMenuTitle(menuRequestDTO.getMenuTitle());
        updatedMenu.setMenuDescription(menuRequestDTO.getMenuDescription());
        return menuRepository.save(updatedMenu);
    }

    public void deleteMenu(Long menuId) throws NotFoundException {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy danh mục có id: " + menuId));
        menuRepository.deleteById(menuId);
    }
    private Dish convertToDish(DishRequestDTO dishRequestDTO) {
        Dish dish = new Dish();
        dish.setDishName(dishRequestDTO.getDishName());
        dish.setDishPrice(dishRequestDTO.getDishPrice());
        dish.setDishStatus(dishRequestDTO.getDishStatus());
        dish.setMenu(null);
        return dish;
    }
}
