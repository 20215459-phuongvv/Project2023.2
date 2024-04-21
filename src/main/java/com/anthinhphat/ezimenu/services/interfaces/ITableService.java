package com.anthinhphat.ezimenu.services.interfaces;

import com.anthinhphat.ezimenu.dtos.TableDTO.TableRequestDTO;
import com.anthinhphat.ezimenu.dtos.TableDTO.TableResponseDTO;
import com.anthinhphat.ezimenu.entities.Table;
import com.anthinhphat.ezimenu.exceptions.BadRequestException;
import com.anthinhphat.ezimenu.exceptions.NotFoundException;

import java.util.List;

public interface ITableService {
    List<TableResponseDTO> getAllTables();
    TableResponseDTO getTableById(Long tableId) throws NotFoundException;
    List<TableResponseDTO> getTablesByStatus(String status) throws NotFoundException;
    Table addTable(TableRequestDTO tableRequestDTO) throws BadRequestException;
    Table updateTable(Long tableId, TableRequestDTO tableRequestDTO) throws NotFoundException, BadRequestException;
    Table updateTableStatus(Long tableId, String status) throws NotFoundException;
    void deleteTable(Long tableId) throws NotFoundException;
}
