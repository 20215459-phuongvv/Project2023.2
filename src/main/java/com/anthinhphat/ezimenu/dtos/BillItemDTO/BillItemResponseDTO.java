package com.anthinhphat.ezimenu.dtos.BillItemDTO;

import lombok.Data;

@Data
public class BillItemResponseDTO {
    private String billItemName;
    private int billItemPrice;
    private int billItemQuantity;
}
