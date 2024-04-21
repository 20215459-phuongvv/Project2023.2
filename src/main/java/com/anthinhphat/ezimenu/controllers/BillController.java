package com.anthinhphat.ezimenu.controllers;

import com.anthinhphat.ezimenu.dtos.BillDTO.BillResponseDTO;
import com.anthinhphat.ezimenu.entities.Bill;
import com.anthinhphat.ezimenu.exceptions.BadRequestException;
import com.anthinhphat.ezimenu.exceptions.NotFoundException;
import com.anthinhphat.ezimenu.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BillController {
    @Autowired
    private BillService billService;
    @GetMapping("/orders/{orderId}/bill")
    public ResponseEntity<?> getBill(@PathVariable Long orderId) throws NotFoundException {
        try{
            BillResponseDTO bill = billService.getBill(orderId);
            if(bill == null) return new ResponseEntity<>("Đơn hàng này chưa có hóa đơn!", HttpStatus.NOT_FOUND);
            return ResponseEntity.ok(bill);
        } catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path = "/admin/orders/{orderId}/bill", method = RequestMethod.POST)
    public ResponseEntity<?> addBill(@PathVariable Long orderId) throws NotFoundException, BadRequestException {
        try{
            Bill bill = billService.addBill(orderId);
            return ResponseEntity.ok(bill);
        } catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(path = "/admin/orders/{orderId}/bill", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBill(@PathVariable Long orderId) throws NotFoundException {
        try{
            billService.deleteBill(orderId);
            return ResponseEntity.ok("Đã xóa bill thành công!");
        } catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
