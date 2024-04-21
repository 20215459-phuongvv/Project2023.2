package com.anthinhphat.ezimenu.services;

import com.anthinhphat.ezimenu.dtos.BillDTO.BillResponseDTO;
import com.anthinhphat.ezimenu.dtos.BillItemDTO.BillItemResponseDTO;
import com.anthinhphat.ezimenu.entities.Bill;
import com.anthinhphat.ezimenu.entities.BillItem;
import com.anthinhphat.ezimenu.entities.Order;
import com.anthinhphat.ezimenu.entities.OrderItem;
import com.anthinhphat.ezimenu.exceptions.BadRequestException;
import com.anthinhphat.ezimenu.exceptions.NotFoundException;
import com.anthinhphat.ezimenu.repositories.BillItemRepository;
import com.anthinhphat.ezimenu.repositories.BillRepository;
import com.anthinhphat.ezimenu.repositories.OrderRepository;
import com.anthinhphat.ezimenu.services.interfaces.IBillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BillService implements IBillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BillItemRepository billItemRepository;
    @Autowired
    private ModelMapper modelMapper;
    public BillResponseDTO getBill(Long orderId) throws NotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy đơn hàng có id: " + orderId));
        BillResponseDTO billResponseDTO = modelMapper.map(order.getBill(), BillResponseDTO.class);
        List<BillItemResponseDTO> billItemResponseDTOS = order.getBill().getBillItems().stream()
                .map(billItem -> modelMapper.map(billItem, BillItemResponseDTO.class))
                .collect(Collectors.toList());
        billResponseDTO.setBillItemResponseDTOS(billItemResponseDTOS);
        return billResponseDTO;
    }
    public Bill addBill(Long orderId) throws NotFoundException, BadRequestException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy đơn hàng có id: " + orderId));
        if(!order.getOrderStatus().equals("Đã hoàn thành")){
            throw new BadRequestException("Thanh toán không thành công do có món chưa ra \nVui lòng xác nhận lại trạng thái món ăn!");
        }
        Bill bill = new Bill();
        bill = billRepository.save(bill);
        Map<String, BillItem> billItemMap = new HashMap<>();
        for(OrderItem orderItem : order.getOrderItems()){
            String dishName = orderItem.getDish().getDishName();
            int dishPrice = (orderItem.getCustomPrice() != 0) ? orderItem.getCustomPrice() : orderItem.getDish().getDishPrice();
            int quantity = orderItem.getDishQuantity();
            String key = dishName + " - " + dishPrice;
            if(billItemMap.containsKey(key)){
                BillItem billItem = billItemMap.get(key);
                billItem.setBillItemQuantity(billItem.getBillItemQuantity() + quantity);
            } else {
                BillItem billItem = new BillItem();
                billItem.setBill(bill);
                billItem.setBillItemName(dishName);
                billItem.setBillItemPrice(dishPrice);
                billItem.setBillItemQuantity(quantity);
                billItemMap.put(key, billItem);
            }
        }
        List<BillItem> billItems = new ArrayList<>(billItemMap.values());
        bill.setOrder(order);
        bill.setBillDateTime(LocalDateTime.now());
        bill.setBillItems(billItems);
        bill.setTotalAmount(calculateTotalAmount(billItems));
        billItemRepository.saveAll(billItems);
        return billRepository.save(bill);
    }
    public void deleteBill(Long orderId) throws NotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Không thể tìm thấy đơn hàng có id: " + orderId));
        Bill bill = order.getBill();
        if(bill == null) throw new NotFoundException("Đơn hàng này không có hóa đơn!");
        bill.setOrder(null);
        billRepository.save(bill);
    }
    private long calculateTotalAmount(List<BillItem> billItems) {
        long totalAmount = 0;
        for (BillItem item : billItems) {
            totalAmount += item.getBillItemPrice() * item.getBillItemQuantity();
        }
        return totalAmount;
    }
}
