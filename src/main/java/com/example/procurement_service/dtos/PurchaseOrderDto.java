package com.example.procurement_service.dtos;

import com.example.procurement_service.enums.PurchaseOrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDto {
    @NotBlank(message = "Procurement Request ID is required.")
    private Long procurementRequestId;
    @NotBlank(message = "Handler's ID is required.")
    private String handledBy;
    @NotBlank(message = "Order Date is required.")
    private LocalDate orderDate;
    @NotBlank(message = "Expected Delivery is required.")
    private LocalDate expectedDelivery;
    @NotBlank(message = "Status is required.")
    private PurchaseOrderStatus status;
    @NotEmpty(message = "At least one purchase item is required.")
    private List<PurchaseItem> purchaseItems;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseItem {
        @NotBlank(message = "Item ID is required.")
        private String itemId;
        @NotBlank(message = "Item Type is required.")
        private String itemType;
        @NotBlank(message = "Quantity is required.")
        private Integer quantity;
    }
}


