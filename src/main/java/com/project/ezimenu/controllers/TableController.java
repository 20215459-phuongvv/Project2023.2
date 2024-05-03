package com.project.ezimenu.controllers;

import com.project.ezimenu.dtos.TableDTO.TableRequestDTO;
import com.project.ezimenu.dtos.TableDTO.TableResponseDTO;
import com.project.ezimenu.entities.Message;
import com.project.ezimenu.entities.Table;
import com.project.ezimenu.exceptions.BadRequestException;
import com.project.ezimenu.exceptions.NotFoundException;
import com.project.ezimenu.services.NotificationService;
import com.project.ezimenu.services.TableService;
import com.project.ezimenu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TableController {
    @Autowired
    private TableService tableService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private MessageController messageController;

    @RequestMapping(path = "/admin/tables", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTables(){
        List<TableResponseDTO> tables = tableService.getAllTables();
        if(tables.isEmpty()){
            return new ResponseEntity<>("Hiện không có bàn nào!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tables);
    }
    @RequestMapping(path = "/admin/tables/{tableId}", method = RequestMethod.GET)
    public ResponseEntity<?> getTableById(@PathVariable Long tableId) throws NotFoundException {
        try{
            TableResponseDTO table = tableService.getTableById(tableId);
            return ResponseEntity.ok(table);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path = "/admin/tables/status", method = RequestMethod.GET)
    public ResponseEntity<?> getTablesByStatus(@RequestParam("status") String status){
        try {
            List<TableResponseDTO> tables = tableService.getTablesByStatus(status);
            return ResponseEntity.ok(tables);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path = "/admin/tables", method = RequestMethod.POST)
    public ResponseEntity<?> addTable(@RequestBody TableRequestDTO tableRequestDTO) throws BadRequestException {
        try{
            Table table = tableService.addTable(tableRequestDTO);
            return new ResponseEntity<Table>(table, HttpStatus.OK);
        } catch(BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/tables/{tableId}/payment/request")
    public ResponseEntity<?> sendPaymentRequest(@PathVariable Long tableId) throws NotFoundException {
        try{
            TableResponseDTO table = tableService.getTableById(tableId);
            tableService.updateTableStatus(tableId, "Đang yêu cầu thanh toán");
            notificationService.addNotification(tableId, "Khách ở bàn " + table.getTableName() + " đang yêu cầu thanh toán");
            Message request = new Message();
            request.setTo("admin");
            request.setText(LocalDateTime.now().format(DateUtils.FORMATTER) + ": Khách ở bàn " + table.getTableName() + " đang yêu cầu thanh toán");
            messageController.sendToSpecificUser(request);
            return new ResponseEntity<>("Yêu cầu đã được gửi đi", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path = "/admin/tables/{tableId}/payment/accept", method = RequestMethod.POST)
    public ResponseEntity<?> acceptPaymentRequest(@PathVariable Long tableId) throws NotFoundException{
        try{
            Table table = tableService.updateTableStatus(tableId, "Đã thanh toán");
            return ResponseEntity.ok(table);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/admin/tables/{tableId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTableName(@PathVariable Long tableId,
                                             @RequestBody TableRequestDTO tableRequestDTO)
            throws NotFoundException
    {
        try{
            Table table = tableService.updateTable(tableId, tableRequestDTO);
            return new ResponseEntity<Table>(table, HttpStatus.ACCEPTED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(path = "/admin/tables/{tableId}/status", method = RequestMethod.PUT)
    public ResponseEntity<?> updateEmptyTable(@PathVariable Long tableId)
            throws NotFoundException
    {
        try{
            Table table = tableService.updateTableStatus(tableId, "Đang trống");
            return new ResponseEntity<Table>(table, HttpStatus.ACCEPTED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path = "/admin/tables/{tableId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteTable(@PathVariable Long tableId) throws NotFoundException {
        try{
            tableService.deleteTable(tableId);
            return new ResponseEntity<>("Đã xóa bàn thành công", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
