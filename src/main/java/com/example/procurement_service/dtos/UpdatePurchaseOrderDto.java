package com.example.procurement_service.dtos;

import com.example.procurement_service.enums.PurchaseOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePurchaseOrderDto {
    private Long purchaseOrderId;
    private String handledBy;
    private LocalDate orderDate;
    private LocalDate expectedDelivery;
    private PurchaseOrderStatus status;
    private List<PurchaseItem> purchaseItems;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PurchaseItem {
        private String itemId;
        private String itemType;
        private Integer quantity;

    }
}
