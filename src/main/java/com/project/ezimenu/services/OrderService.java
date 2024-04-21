package com.project.ezimenu.services;

import com.project.ezimenu.dtos.OrderDTO.OrderResponseDTO;
import com.project.ezimenu.dtos.OrderItemDTO.OrderItemRequestDTO;
import com.project.ezimenu.dtos.OrderItemDTO.OrderItemResponseDTO;
import com.project.ezimenu.entities.Dish;
import com.project.ezimenu.entities.Order;
import com.project.ezimenu.entities.OrderItem;
import com.project.ezimenu.entities.Table;
import com.project.ezimenu.exceptions.NotFoundException;
import com.project.ezimenu.repositories.DishRepository;
import com.project.ezimenu.repositories.OrderItemRepository;
import com.project.ezimenu.repositories.OrderRepository;
import com.project.ezimenu.repositories.TableRepository;
import com.project.ezimenu.services.interfaces.IOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private ModelMapper modelMapper;
    private Sort sortByTimeAsc = Sort.by(Sort.Direction.ASC, "orderTime");
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll(sortByTimeAsc);
        return orders.stream()
                .map(order -> {
                    OrderResponseDTO orderResponseDTO = modelMapper.map(order, OrderResponseDTO.class);
                    List<OrderItemResponseDTO> orderItemResponseDTOS = order.getOrderItems().stream()
                            .map(orderItem -> {
                                OrderItemResponseDTO orderItemResponseDTO = modelMapper.map(orderItem, OrderItemResponseDTO.class);
                                orderItemResponseDTO.setDishName(orderItem.getDish().getDishName());
                                orderItemResponseDTO.setCustomPrice((orderItem.getCustomPrice() == 0) ? orderItem.getDish().getDishPrice() : orderItem.getCustomPrice());
                                return orderItemResponseDTO;
                            })
                            .collect(Collectors.toList());
                    orderResponseDTO.setOrderItemResponseDTO(orderItemResponseDTOS);
                    return orderResponseDTO;
                })
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getOrderResponseById(Long orderId) throws NotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy đơn hàng với id: " + orderId));
        OrderResponseDTO orderResponseDTO = modelMapper.map(order, OrderResponseDTO.class);
        List<OrderItemResponseDTO> orderItemResponseDTOS = order.getOrderItems().stream()
                .map(orderItem -> {
                    OrderItemResponseDTO orderItemResponseDTO = modelMapper.map(orderItem, OrderItemResponseDTO.class);
                    orderItemResponseDTO.setDishName(orderItem.getDish().getDishName());
                    orderItemResponseDTO.setCustomPrice((orderItem.getCustomPrice() == 0) ? orderItem.getDish().getDishPrice() : orderItem.getCustomPrice());
                    return orderItemResponseDTO;
                })
                .collect(Collectors.toList());
        orderResponseDTO.setOrderItemResponseDTO(orderItemResponseDTOS);
        return orderResponseDTO;
    }
    public OrderResponseDTO getOrderResponseByTableId(Long tableId) throws NotFoundException {
        Table table = tableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy bàn có id: " + tableId));
        if(table.getTableStatus().equals("Đang trống")){
            throw new NotFoundException("Bàn này chưa có order nào!");
        }
        Order order = table.getOrders().get(table.getOrders().size() - 1);
        List<OrderItemResponseDTO> orderItemResponseDTOS = order.getOrderItems().stream()
                .map(orderItem -> {
                    OrderItemResponseDTO orderItemResponseDTO = modelMapper.map(orderItem, OrderItemResponseDTO.class);
                    orderItemResponseDTO.setDishName(orderItem.getDish().getDishName());
                    orderItemResponseDTO.setCustomPrice((orderItem.getCustomPrice() == 0) ? orderItem.getDish().getDishPrice() : orderItem.getCustomPrice());
                    return orderItemResponseDTO;
                })
                .collect(Collectors.toList());
        OrderResponseDTO orderResponseDTO = modelMapper.map(order, OrderResponseDTO.class);
        orderResponseDTO.setOrderItemResponseDTO(orderItemResponseDTOS);
        return orderResponseDTO;
    }
    public Order getOrderById(Long orderId) throws NotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy đơn hàng với id: " + orderId));
        return order;
    }
    public Order createEmptyOrder(Long tableId) throws NotFoundException {
        Table table = tableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy bàn có id: " + tableId));
        Order newOrder = new Order();
        newOrder.setTable(table);
        newOrder.setOrderTime(LocalDateTime.now());
        newOrder.setOrderStatus("Đang order");
        return orderRepository.save(newOrder);
    }
    public Order sendOrder(Long tableId) throws NotFoundException {
        Table table = tableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy bàn có id: " + tableId));
        table.setTableStatus("Đang phục vụ");
        Order order = table.getOrders().get(table.getOrders().size() - 1);
        order.setOrderTime(LocalDateTime.now());
        order.setOrderStatus("Đang ra món");
        order = orderRepository.save(order);
        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            List<OrderItem> orderItems = order.getOrderItems()
                    .stream()
                    .map(orderItem -> {
                        if(orderItem.getDishStatus().equals("Đang chọn")){
                            orderItem.setDishStatus("Đang ra món");
                        }
                       return orderItem;
                    })
                    .collect(Collectors.toList());
            orderItemRepository.saveAll(orderItems);
        }
        tableRepository.save(table);
        return orderRepository.save(order);
    }
    public void deleteOrder(Long orderId) throws NotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy đơn hàng với id: " + orderId));
        orderRepository.deleteById(orderId);
    }
    private OrderItem convertToOrderItem(OrderItemRequestDTO orderItemRequestDTO) throws NotFoundException {
        OrderItem orderItem = new OrderItem();
        Dish dish = dishRepository.findById(orderItemRequestDTO.getDishId())
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy món ăn đặt hàng có id: " + orderItemRequestDTO.getDishId()));
        orderItem.setDish(dish);
        orderItem.setDishQuantity(orderItemRequestDTO.getDishQuantity());
        orderItem.setDishNote(orderItemRequestDTO.getDishNote());
        orderItem.setDishStatus(orderItemRequestDTO.getDishStatus());
        orderItem.setOrder(null);
        return orderItem;
    }
}
