package com.project.ezimenu.services;

import com.project.ezimenu.dtos.NotificationDTO.NotificationResponseDTO;
import com.project.ezimenu.dtos.TableDTO.TableRequestDTO;
import com.project.ezimenu.dtos.TableDTO.TableResponseDTO;
import com.project.ezimenu.entities.Notification;
import com.project.ezimenu.entities.Order;
import com.project.ezimenu.entities.OrderItem;
import com.project.ezimenu.entities.Table;
import com.project.ezimenu.exceptions.BadRequestException;
import com.project.ezimenu.exceptions.NotFoundException;
import com.project.ezimenu.repositories.NotificationRepository;
import com.project.ezimenu.repositories.OrderRepository;
import com.project.ezimenu.repositories.TableRepository;
import com.project.ezimenu.services.interfaces.ITableService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TableService implements ITableService {
    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<TableResponseDTO> getAllTables() {
        List<Table> tables = tableRepository.findAll();
        return tables.stream()
                .map(table -> {
                    int doneDish = 0;
                    int totalDish = 0;
                    long minutes = 0;
                    if(!"Đang trống".equals(table.getTableStatus()) && !table.getOrders().isEmpty() && table.getOrders() != null){
                        for (OrderItem orderItem : table.getOrders().get(table.getOrders().size() - 1).getOrderItems()) {
                            if ("Đã ra món".equals(orderItem.getDishStatus())) {
                                doneDish += orderItem.getDishQuantity();
                            }
                            totalDish += orderItem.getDishQuantity();
                        }
                        Duration duration = Duration.between(table.getStartOrderingTime(), LocalDateTime.now());
                        minutes = duration.toMinutes();
                    }
                    TableResponseDTO tableResponseDTO = modelMapper.map(table, TableResponseDTO.class);
                    List<NotificationResponseDTO> notificationResponseDTOS = table.getNotifications().stream()
                            .map(notification -> modelMapper.map(notification, NotificationResponseDTO.class))
                            .collect(Collectors.toList());
                    tableResponseDTO.setNotificationNumber(notificationResponseDTOS.size());
                    tableResponseDTO.setDoneDish(doneDish);
                    tableResponseDTO.setTotalDish(totalDish);
                    tableResponseDTO.setTotalTime(minutes);
                    return tableResponseDTO;
                })
                .collect(Collectors.toList());
    }
    public TableResponseDTO getTableById(Long tableId) throws NotFoundException {
        Table table = tableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy bàn có id: " + tableId));
        int doneDish = 0;
        int totalDish = 0;
        long minutes = 0;
        if(!"Đang trống".equals(table.getTableStatus())){
            for (OrderItem orderItem : table.getOrders().get(table.getOrders().size() - 1).getOrderItems()) {
                if ("Đã ra món".equals(orderItem.getDishStatus())) {
                    doneDish += orderItem.getDishQuantity();
                }
                totalDish += orderItem.getDishQuantity();
            }
            Duration duration = Duration.between(table.getStartOrderingTime(), LocalDateTime.now());
            minutes = duration.toMinutes();
        }
        TableResponseDTO tableResponseDTO = modelMapper.map(table, TableResponseDTO.class);
        List<NotificationResponseDTO> notificationResponseDTOS = table.getNotifications().stream()
                .map(notification -> modelMapper.map(notification, NotificationResponseDTO.class))
                .collect(Collectors.toList());
        tableResponseDTO.setDoneDish(doneDish);
        tableResponseDTO.setTotalDish(totalDish);
        tableResponseDTO.setNotificationNumber(notificationResponseDTOS.size());
        tableResponseDTO.setTotalTime(minutes);
        return tableResponseDTO;
    }
    public List<TableResponseDTO> getTablesByStatus(String status) throws NotFoundException {
        if("Đang hoạt động".equalsIgnoreCase(status)){
            List<Table> tables = tableRepository.findAll();
            List<Table> emptyTables = tableRepository.findByTableStatusIgnoreCase("Đang trống")
                    .orElseThrow(() -> new NotFoundException("Không có bàn nào có trạng thái: " + status));
            tables.removeAll(emptyTables);
            return tables.stream()
                    .map(table -> {
                        int doneDish = 0;
                        int totalDish = 0;
                        long minutes = 0;
                        if(!"Đang trống".equals(table.getTableStatus())){
                            for (OrderItem orderItem : table.getOrders().get(table.getOrders().size() - 1).getOrderItems()) {
                                if ("Đã ra món".equals(orderItem.getDishStatus())) {
                                    doneDish += orderItem.getDishQuantity();
                                }
                                totalDish += orderItem.getDishQuantity();
                            }
                            Duration duration = Duration.between(table.getStartOrderingTime(), LocalDateTime.now());
                            minutes = duration.toMinutes();
                        }
                        TableResponseDTO tableResponseDTO = modelMapper.map(table, TableResponseDTO.class);
                        List<NotificationResponseDTO> notificationResponseDTOS = table.getNotifications().stream()
                                .map(notification -> modelMapper.map(notification, NotificationResponseDTO.class))
                                .collect(Collectors.toList());
                        tableResponseDTO.setNotificationNumber(notificationResponseDTOS.size());
                        tableResponseDTO.setDoneDish(doneDish);
                        tableResponseDTO.setTotalDish(totalDish);
                        tableResponseDTO.setTotalTime(minutes);
                        return tableResponseDTO;
                    })
                    .collect(Collectors.toList());
        }
        List<Table> tables = tableRepository.findByTableStatusIgnoreCase(status)
                .orElseThrow(() -> new NotFoundException("Không có bàn nào có trạng thái: " + status));
        return tables.stream()
                .map(table -> {
                    int doneDish = 0;
                    int totalDish = 0;
                    long minutes = 0;
                    if(!"Đang trống".equals(table.getTableStatus())){
                        for (OrderItem orderItem : table.getOrders().get(table.getOrders().size() - 1).getOrderItems()) {
                            if ("Đã ra món".equals(orderItem.getDishStatus())) {
                                doneDish += orderItem.getDishQuantity();
                            }
                            totalDish += orderItem.getDishQuantity();
                        }
                        Duration duration = Duration.between(table.getStartOrderingTime(), LocalDateTime.now());
                        minutes = duration.toMinutes();
                    }
                    TableResponseDTO tableResponseDTO = modelMapper.map(table, TableResponseDTO.class);
                    List<NotificationResponseDTO> notificationResponseDTOS = table.getNotifications().stream()
                            .map(notification -> modelMapper.map(notification, NotificationResponseDTO.class))
                            .collect(Collectors.toList());
                    tableResponseDTO.setNotificationNumber(notificationResponseDTOS.size());
                    tableResponseDTO.setDoneDish(doneDish);
                    tableResponseDTO.setTotalDish(totalDish);
                    tableResponseDTO.setTotalTime(minutes);
                    return tableResponseDTO;
                })
                .collect(Collectors.toList());
    }
    public Table addTable(TableRequestDTO tableRequestDTO) throws BadRequestException {
        if(tableRequestDTO.getTableName() == null || "".equals(tableRequestDTO.getTableName())){
            throw new BadRequestException("Vui lòng nhập đầy đủ thông tin!");
        }
        Optional<Table> existingTable = tableRepository.findByTableName(tableRequestDTO.getTableName());
        if(existingTable.isPresent()){
            throw new BadRequestException("Bàn có tên: " + tableRequestDTO.getTableName() + " đã tồn tại!");
        }

        Table newTable = new Table();
        newTable.setTableStatus("Đang trống");
        newTable.setTableName(tableRequestDTO.getTableName());
        return tableRepository.save(newTable);
    }
    public Table updateTable(Long tableId, TableRequestDTO tableRequestDTO) throws NotFoundException, BadRequestException {
        if(tableRequestDTO.getTableName() == null || "".equals(tableRequestDTO.getTableName())){
            throw new BadRequestException("Vui lòng nhập đầy đủ thông tin!");
        }
        Table table = tableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy bàn có id: " + tableId));
        Optional<Table> existingTable = tableRepository.findByTableName(tableRequestDTO.getTableName());
        if(existingTable.isPresent()){
            throw new BadRequestException("Bàn có tên: " + tableRequestDTO.getTableName() + " đã tồn tại!");
        }
        table.setTableName(tableRequestDTO.getTableName());
        return tableRepository.save(table);
    }
    public Table updateTableStatus(Long tableId, String status) throws NotFoundException {
        Table table = tableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy bàn có id: " + tableId));
        table.setTableStatus(status);
        if(status.equals("Đã thanh toán")){
            Order order = table.getOrders().get(table.getOrders().size() - 1);
            order.setOrderStatus("Đã thanh toán");
            orderRepository.save(order);
        }
        if(status.equals("Đang trống")){
            for(Notification notification : table.getNotifications()){
                notification.setTable(null);
            }
//            table.setNotifications(null);
            table.setStartOrderingTime(null);
        }
        return tableRepository.save(table);
    }
    public void deleteTable(Long tableId) throws NotFoundException {
        Table table = tableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy bàn có id: " + tableId));
        List<Order> orders = orderRepository.findByTable(table);
        if(!orders.isEmpty()){
            for (Order order : orders) {
                order.setTable(null);
                orderRepository.save(order);
            }
        }
        tableRepository.deleteById(tableId);
    }
}
