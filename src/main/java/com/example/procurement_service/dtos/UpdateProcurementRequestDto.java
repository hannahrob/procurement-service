package com.example.procurement_service.dtos;

import com.example.procurement_service.enums.RequestStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProcurementRequestDto {
    @NotBlank(message = "Procurement ID is required.")
    private Long procurementRequestId;
    private String department;
    private String itemId;
    private String itemType;
    private Integer quantity;
    @NotBlank(message = "Update Date is required.")
    private LocalDate updatedAt;
    private String staffId;
    private RequestStatus status;
}
