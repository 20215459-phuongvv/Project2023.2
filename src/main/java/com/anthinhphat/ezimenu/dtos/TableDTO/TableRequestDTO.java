package com.anthinhphat.ezimenu.dtos.TableDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TableRequestDTO {
    @NotBlank
    private String tableName;
    private String tableStatus;
}
