package com.example.procurement_service.entity;

import com.example.procurement_service.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "procurement_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcurementRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "procurement_request_id")
    private Long procurementRequestId;
    @Column(name = "department")
    private String department;
    @Column(name = "item_id")
    private String itemId;
    @Column(name = "item_type")
    private String itemType;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "staff_id")
    private String staffId;
    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
