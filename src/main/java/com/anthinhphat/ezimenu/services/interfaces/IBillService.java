package com.anthinhphat.ezimenu.services.interfaces;

import com.anthinhphat.ezimenu.dtos.BillDTO.BillResponseDTO;
import com.anthinhphat.ezimenu.entities.Bill;
import com.anthinhphat.ezimenu.exceptions.BadRequestException;
import com.anthinhphat.ezimenu.exceptions.NotFoundException;

public interface IBillService {
    BillResponseDTO getBill(Long orderId)throws NotFoundException;
    Bill addBill(Long orderId) throws NotFoundException, BadRequestException;
    void deleteBill(Long orderId) throws NotFoundException;
}
