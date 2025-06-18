package com.example.procurement_service.repository;

import com.example.procurement_service.entity.ProcurementRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcurementRequestRepository extends JpaRepository<ProcurementRequestEntity, Long> {
    @Query(nativeQuery = true,value = "SELECT * FROM procurement_request WHERE procurement_request_id = :procurementRequestId")
    Optional<ProcurementRequestEntity> findByProcurementRequestId(@Param("procurementRequestId") Long procurementRequestId);

    @Query(nativeQuery = true, value = "SELECT * FROM procurement_request WHERE department = :department")
    List<ProcurementRequestEntity> findByDepartment(@Param ("department") String department);
}
