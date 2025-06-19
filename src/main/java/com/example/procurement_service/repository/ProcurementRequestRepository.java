package com.example.procurement_service.repository;

import com.example.procurement_service.entity.ProcurementRequestEntity;
import com.example.procurement_service.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProcurementRequestRepository extends JpaRepository<ProcurementRequestEntity, Long> {
    @Query(nativeQuery = true,value = "SELECT * FROM procurement_request WHERE procurement_request_id = :procurementRequestId")
    Optional<ProcurementRequestEntity> findByProcurementRequestId(@Param("procurementRequestId") Long procurementRequestId);

    @Query(nativeQuery = true, value = "SELECT * FROM procurement_request WHERE department = :department")
    List<ProcurementRequestEntity> findByDepartment(@Param ("department") String department);

    @Query(nativeQuery = true, value = "SELECT * FROM procurement_request WHERE staff_id = :staffId")
    List<ProcurementRequestEntity> findByStaffId(@Param ("staffId") String staffId);

    @Query(nativeQuery = true, value = "SELECT * FROM procurement_request WHERE DATE(created_at) >= :startDate and DATE(created_at) >= :endDate")
    List<ProcurementRequestEntity> findByCreatedAt(@Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);

    @Query(nativeQuery = true, value = "SELECT * FROM procurement_request WHERE status = :status")
    List<ProcurementRequestEntity> findByStatus(RequestStatus status);

    @Query(nativeQuery = true, value = "DELETE * FROM procurement_request WHERE procurement_request_id = :procurementRequestId")
    void deleteByProcurementRequestId(@Param("procurementRequestId") Long procurementRequestId);
}
