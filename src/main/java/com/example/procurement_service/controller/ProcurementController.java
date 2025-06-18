package com.example.procurement_service.controller;

import com.example.procurement_service.dtos.ProcurementRequestDto;
import com.example.procurement_service.dtos.ProcurementResponse;
import com.example.procurement_service.dtos.UpdateProcurementRequestDto;
import com.example.procurement_service.service.ProcurementRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/procurement")
@RequiredArgsConstructor
public class ProcurementController {
    private ProcurementRequestService procurementRequestService;

    @PostMapping("/create-request")
    public ResponseEntity<ProcurementResponse> createRequest(@RequestBody ProcurementRequestDto procurementRequestDto){
        return procurementRequestService.createProcurementRequest(procurementRequestDto);
    }

    @PutMapping("/update-request")
    public ResponseEntity<ProcurementResponse> updateRequest(@RequestBody UpdateProcurementRequestDto updateProcurementRequestDto){
        return procurementRequestService.updateProcurementRequest(updateProcurementRequestDto);
    }
    @GetMapping("/get-by-department")
    public ResponseEntity<ProcurementResponse> getRequestByDepartment(@RequestParam("department") String department){
        return procurementRequestService.getProcurementRequestByDepartment(department);
    }
}
