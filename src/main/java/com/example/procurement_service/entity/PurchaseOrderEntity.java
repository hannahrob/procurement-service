package com.example.procurement_service.entity;

import com.example.procurement_service.dtos.PurchaseOrderDto;
import com.example.procurement_service.enums.PurchaseOrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "purchase_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_order_id")
    private Long purchaseOrderId;
    @Column(name = "procurement_request_id")
    private Long procurementRequestId;
    @Column(name = "handled_by")
    private String handledBy;
    @Column(name = "order_date")
    private LocalDate orderDate;
    @Column(name = "expected_delivery")
    private LocalDate expectedDelivery;
    @Column(name = "status")
    private PurchaseOrderStatus status;
    @Column(name = "Prescription", columnDefinition = "TEXT")
    private String purchaseItems;

}
