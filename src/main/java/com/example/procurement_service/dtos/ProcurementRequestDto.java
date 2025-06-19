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
public class ProcurementRequestDto {
    @NotBlank(message = "department is required.")
    private String department;
    @NotBlank(message = "Item ID is required.")
    private String itemId;
    @NotBlank(message = "Item Type is required.")
    private String itemType;
    @NotBlank(message = "Quantity is required.")
    private Integer quantity;
    @NotBlank(message = "Staff ID is required")
    private String staffId;
    @NotBlank(message = "Date Created is required")
    private LocalDate createdAt;
    @NotBlank(message = "Status is required")
    private RequestStatus status;
}
