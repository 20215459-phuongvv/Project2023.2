package com.anthinhphat.ezimenu.dtos.BillDTO;
import com.anthinhphat.ezimenu.dtos.BillItemDTO.BillItemResponseDTO;
import lombok.Data;

import java.util.List;
@Data
public class BillResponseDTO {
    private long billId;
    private long orderId;
    private long totalAmount;
    private String billDateTime;
    private List<BillItemResponseDTO> billItemResponseDTOS;
}
