package com.example.procurement_service.service;

import com.example.procurement_service.dtos.ProcurementRequestDto;
import com.example.procurement_service.dtos.ProcurementResponse;
import com.example.procurement_service.dtos.ProcurementDateDto;
import com.example.procurement_service.dtos.UpdateProcurementRequestDto;
import com.example.procurement_service.entity.ProcurementRequestEntity;
import com.example.procurement_service.enums.RequestStatus;
import com.example.procurement_service.repository.ProcurementRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProcurementRequestService {
    private final ProcurementRequestRepository procurementRequestRepository;

    public ResponseEntity<ProcurementResponse> createProcurementRequest(ProcurementRequestDto procurementRequestDto) {
        try {
            ProcurementRequestEntity newProcurementRequestEntity = ProcurementRequestEntity.builder()
                    .department(procurementRequestDto.getDepartment())
                    .itemId(procurementRequestDto.getItemId())
                    .itemType(procurementRequestDto.getItemType())
                    .quantity(procurementRequestDto.getQuantity())
                    .staffId(procurementRequestDto.getStaffId())
                    .createdAt(procurementRequestDto.getCreatedAt())
                    .status(procurementRequestDto.getStatus())
                    .build();
            procurementRequestRepository.save(newProcurementRequestEntity);
        } catch (Exception e) {
            return new ResponseEntity<>(new ProcurementResponse("99", "Procurement Failed To Create "), HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(new ProcurementResponse("00", "Procurement Request has been created"), HttpStatus.OK);
    }

    public ResponseEntity<ProcurementResponse> updateProcurementRequest(UpdateProcurementRequestDto updateProcurementRequestDto) {
        try {
            Optional<ProcurementRequestEntity> existingOpt = procurementRequestRepository.findByProcurementRequestId(updateProcurementRequestDto.getProcurementRequestId());
            if (existingOpt.isEmpty()) {
                return new ResponseEntity<>(new ProcurementResponse("99", "Procurement Request does not exist"), HttpStatus.NOT_FOUND);
            }
            ProcurementRequestEntity existingRequest = existingOpt.get();
            existingRequest.setProcurementRequestId(updateProcurementRequestDto.getProcurementRequestId());
            if (updateProcurementRequestDto.getDepartment() != null) {
                existingRequest.setDepartment(updateProcurementRequestDto.getDepartment());
            }
            if (updateProcurementRequestDto.getItemId() != null) {
                existingRequest.setItemId(updateProcurementRequestDto.getItemId());
            }
            if (updateProcurementRequestDto.getItemType() != null) {
                existingRequest.setItemType(updateProcurementRequestDto.getItemType());
            }
            if (updateProcurementRequestDto.getQuantity() != null) {
                existingRequest.setQuantity(updateProcurementRequestDto.getQuantity());
            }
            if (updateProcurementRequestDto.getStaffId() != null) {
                existingRequest.setStaffId(updateProcurementRequestDto.getStaffId());
            }
            if (updateProcurementRequestDto.getUpdatedAt() != null) {
                existingRequest.setStatus(updateProcurementRequestDto.getStatus());
            }
            if (updateProcurementRequestDto.getStatus() != null) {
                existingRequest.setStatus(updateProcurementRequestDto.getStatus());
            }
            procurementRequestRepository.save(existingRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(new ProcurementResponse("100", "Procurement Request Failed to Update"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ProcurementResponse("00", "Procurement Request has been saved successfully"), HttpStatus.OK);
    }

    public ResponseEntity<ProcurementResponse> getProcurementRequestByDepartment(String department) {
        List<ProcurementRequestEntity> procurementRequestEntities = procurementRequestRepository.findByDepartment(department);
        if (procurementRequestEntities.isEmpty()) {
            return new ResponseEntity<>(new ProcurementResponse("99", "Procurement Request for this department does not exist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ProcurementResponse("00", "The procurement request for this department are", procurementRequestEntities), HttpStatus.OK);
    }

    public ResponseEntity<ProcurementResponse> getProcurementRequestByStaffId(String staffId) {
        List<ProcurementRequestEntity> procurementRequestEntities = procurementRequestRepository.findByStaffId(staffId);
        if (procurementRequestEntities.isEmpty()) {
            return new ResponseEntity<>(new ProcurementResponse("99", "Procurement Request for this staff Id does not exist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ProcurementResponse("00", "The procurement request for this staff Id are", procurementRequestEntities), HttpStatus.OK);
    }

    public ResponseEntity<ProcurementResponse> getProcurementRequestByDateRange(ProcurementDateDto requestDateDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(requestDateDto.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(requestDateDto.getEndDate(), formatter);
        List<ProcurementRequestEntity> opt = procurementRequestRepository.findByCreatedAt(startDate, endDate);
        if (opt.isEmpty()) {
            return new ResponseEntity<>(new ProcurementResponse("99", "No available Procurement Request within this date range"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ProcurementResponse("00", "Procurement Request found", opt), HttpStatus.OK);
    }

    public ResponseEntity<ProcurementResponse> getProcurementRequestByStatus(RequestStatus status) {
        List<ProcurementRequestEntity> procurementRequestEntities = procurementRequestRepository.findByStatus(status);
        if (procurementRequestEntities.isEmpty()) {
            return new ResponseEntity<>(new ProcurementResponse("99", "Procurement Request with this status does not exist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ProcurementResponse("00", "The procurement request with this status are", procurementRequestEntities), HttpStatus.OK);
    }

    public List<ProcurementRequestEntity> getAllProcurementRequests(){
        return procurementRequestRepository.findAll();
    }

    public ResponseEntity<ProcurementResponse> deleteProcurementRequest(Long procurementRequestId){
        procurementRequestRepository.deleteByProcurementRequestId(procurementRequestId);
        return new ResponseEntity<>(new ProcurementResponse("00","Procurement Request has been deleted successfully"),HttpStatus.OK);
    }
}

