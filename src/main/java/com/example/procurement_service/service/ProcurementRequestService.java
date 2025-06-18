package com.example.procurement_service.service;

import com.example.procurement_service.dtos.ProcurementRequestDto;
import com.example.procurement_service.dtos.ProcurementResponse;
import com.example.procurement_service.dtos.UpdateProcurementRequestDto;
import com.example.procurement_service.entity.ProcurementRequestEntity;
import com.example.procurement_service.repository.ProcurementRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
                    .status(procurementRequestDto.getStatus())
                    .build();
            procurementRequestRepository.save(newProcurementRequestEntity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            if (updateProcurementRequestDto.getStatus() != null) {
                existingRequest.setStatus(updateProcurementRequestDto.getStatus());
            }
            procurementRequestRepository.save(existingRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(new ProcurementResponse("100", "Procurement Request Failed to Update"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ProcurementResponse("00", "Procurement Request has been saved successfully"), HttpStatus.OK);
    }

    public ResponseEntity<ProcurementResponse> getProcurementRequestByDepartment(String department){
        L
    }
}
