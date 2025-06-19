package com.example.procurement_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcurementDateDto {
    private String startDate;
    private String endDate;
}
