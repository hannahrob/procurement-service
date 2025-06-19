package com.example.procurement_service.controller;

import com.example.procurement_service.dtos.*;
import com.example.procurement_service.entity.ProcurementRequestEntity;
import com.example.procurement_service.entity.PurchaseOrderEntity;
import com.example.procurement_service.enums.PurchaseOrderStatus;
import com.example.procurement_service.enums.RequestStatus;
import com.example.procurement_service.service.ProcurementRequestService;
import com.example.procurement_service.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/procurement")
@RequiredArgsConstructor
public class ProcurementController {
    private final ProcurementRequestService procurementRequestService;
    private final PurchaseOrderService purchaseOrderService;

    @PostMapping("/create-request")
    public ResponseEntity<ProcurementResponse> createRequest(@RequestBody ProcurementRequestDto procurementRequestDto){
        return procurementRequestService.createProcurementRequest(procurementRequestDto);
    }

    @PutMapping("/update-request")
    public ResponseEntity<ProcurementResponse> updateRequest(@RequestBody UpdateProcurementRequestDto updateProcurementRequestDto){
        return procurementRequestService.updateProcurementRequest(updateProcurementRequestDto);
    }
    @GetMapping("/get-request-by-department/{department}")
    public ResponseEntity<ProcurementResponse> getRequestByDepartment(@RequestParam("department") String department){
        return procurementRequestService.getProcurementRequestByDepartment(department);
    }

    @GetMapping("/get-request-by-staff-id/{staffId}")
    public ResponseEntity<ProcurementResponse> getRequestByStaffId(@RequestParam("staffId") String staffId){
        return procurementRequestService.getProcurementRequestByStaffId(staffId);
    }

    @GetMapping("/get-request-by-date-range")
    public ResponseEntity<ProcurementResponse> getRequestByDate(@RequestBody ProcurementDateDto requestDateDto){
        return procurementRequestService.getProcurementRequestByDateRange(requestDateDto);
    }
    @GetMapping("/get-request-by-status/{status}")
    public ResponseEntity<ProcurementResponse> getRequestByStatus(@PathVariable RequestStatus status){
        return procurementRequestService.getProcurementRequestByStatus(status);
    }
    @GetMapping("/get-all-requests")
    public List<ProcurementRequestEntity> getAllRequests(){
        return procurementRequestService.getAllProcurementRequests();
    }
    @DeleteMapping("/delete-request/{procurementRequestId}")
    public ResponseEntity<ProcurementResponse> deleteByProcurementRequestId(@PathVariable ("procurementRequestId") Long procurementRequestId){
        return procurementRequestService.deleteProcurementRequest(procurementRequestId);
    }

    @PostMapping("/create-order")
    public ResponseEntity<ProcurementResponse> createPurchaseOrder(@RequestBody PurchaseOrderDto purchaseOrderDto){
        return purchaseOrderService.createPurchaseOrder(purchaseOrderDto);
    }

    @PutMapping("/update-order")
    public ResponseEntity<ProcurementResponse> updatePurchaseOrder(@RequestBody UpdatePurchaseOrderDto updatePurchaseOrderDto){
        return purchaseOrderService.updatePurchaseOrder(updatePurchaseOrderDto);
    }

    @GetMapping("/get-order-by-request-id/{procurementRequestId}")
    public ResponseEntity<ProcurementResponse> getPurchaseOrderByRequestId(@RequestParam("procurementRequestId") Long procurementRequestId){
        return purchaseOrderService.getOrderByRequestId(procurementRequestId);
    }

    @GetMapping("/get-order-by-status/{status}")
    public ResponseEntity<ProcurementResponse> getPurchaseOrderByStatus(@PathVariable PurchaseOrderStatus status){
        return purchaseOrderService.getOrderByStatus(status);
    }

    @GetMapping("/get-order-by-handler/{handledBy}")
    public ResponseEntity<ProcurementResponse> getPurchaseOrderByHandler(@RequestParam("handledBy") String handledBy){
        return purchaseOrderService.getOrderByHandler(handledBy);
    }
    @GetMapping("/get-order-by-date-range")
    public ResponseEntity<ProcurementResponse> getPurchaseOrderByDate(@RequestBody ProcurementDateDto procurementDateDto){
        return purchaseOrderService.getOrderByDateRange(procurementDateDto);
    }
    @GetMapping("/get-all-orders")
    public List<PurchaseOrderEntity> getAllPurchaseOrders(){
        return purchaseOrderService.getAllOrders();
    }

    @DeleteMapping("/delete-order/{procurementRequestId}")
    public ResponseEntity<ProcurementResponse> deleteByRequestId(@PathVariable ("procurementRequestId") Long procurementRequestId){
        return purchaseOrderService.deletePurchaseOrder(procurementRequestId);
    }
}
