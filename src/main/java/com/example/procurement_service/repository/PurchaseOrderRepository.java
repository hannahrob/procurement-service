package com.example.procurement_service.repository;

import com.example.procurement_service.entity.ProcurementRequestEntity;
import com.example.procurement_service.entity.PurchaseOrderEntity;
import com.example.procurement_service.enums.PurchaseOrderStatus;
import com.example.procurement_service.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM purchase_order WHERE procurement_request_id = :procurementRequestId")
        Optional<PurchaseOrderEntity> findByProcurementRequestId(@Param("procurementRequestId") Long procurementRequestId);

    @Query(nativeQuery = true, value = "SELECT * FROM purchase_order WHERE status = :status")
    List<PurchaseOrderEntity> findByStatus(PurchaseOrderStatus status);

    @Query(nativeQuery = true, value = "SELECT * FROM purchase_order WHERE handled_by = :handledBy")
    List<PurchaseOrderEntity> findByHandler(@Param ("handledBy") String handledBy);

    @Query(nativeQuery = true, value = "SELECT * FROM purchase_order WHERE DATE(order_date) >= :startDate and DATE(order_date) >= :endDate")
    List<PurchaseOrderEntity> findByOrderDate(@Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);

    @Query(nativeQuery = true, value = "DELETE * FROM purchase_order WHERE procurement_request_id = :procurementRequestId")
    void deleteByRequestId(@Param("procurementRequestId") Long procurementRequestId);
}
