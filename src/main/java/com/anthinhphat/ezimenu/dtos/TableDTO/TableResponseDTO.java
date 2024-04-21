package com.anthinhphat.ezimenu.dtos.TableDTO;

import lombok.Data;

@Data
public class TableResponseDTO {
    private long tableId;
    private String tableName;
    private String tableStatus;
    private int notificationNumber;
    private int doneDish;
    private int totalDish;
    private long totalTime;
}
