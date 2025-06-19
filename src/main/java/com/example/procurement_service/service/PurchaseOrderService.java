package com.example.procurement_service.service;

import com.example.procurement_service.dtos.ProcurementDateDto;
import com.example.procurement_service.dtos.ProcurementResponse;
import com.example.procurement_service.dtos.PurchaseOrderDto;
import com.example.procurement_service.dtos.UpdatePurchaseOrderDto;
import com.example.procurement_service.dtos.dtos.transit.Items;
import com.example.procurement_service.entity.PurchaseOrderEntity;
import com.example.procurement_service.enums.PurchaseOrderStatus;
import com.example.procurement_service.repository.PurchaseOrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ObjectMapper objectMapper;

    public ResponseEntity<ProcurementResponse> createPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
        try {
            Optional<PurchaseOrderEntity> existingOrder = purchaseOrderRepository.findByProcurementRequestId(purchaseOrderDto.getProcurementRequestId());
            if (existingOrder.isPresent()) {
                return new ResponseEntity<>(new ProcurementResponse("99", "Purchase Order has already been created for this request"), HttpStatus.CONFLICT);
            }
            PurchaseOrderEntity purchaseOrderEntity = new PurchaseOrderEntity();
            purchaseOrderEntity.setProcurementRequestId(purchaseOrderDto.getProcurementRequestId());
            purchaseOrderEntity.setHandledBy(purchaseOrderDto.getHandledBy());
            purchaseOrderEntity.setOrderDate(purchaseOrderDto.getOrderDate());
            purchaseOrderEntity.setExpectedDelivery(purchaseOrderDto.getExpectedDelivery());
            purchaseOrderEntity.setStatus(purchaseOrderDto.getStatus());

            List<Items> purchaseItems = new ArrayList<>();
            for (PurchaseOrderDto.PurchaseItem item : purchaseOrderDto.getPurchaseItems()) {
                Items orderItem = new Items();
                orderItem.setItemId(item.getItemId());
                orderItem.setItemType(item.getItemType());
                orderItem.setQuantity(item.getQuantity());
                purchaseItems.add(orderItem);
            }
            try {
                purchaseOrderEntity.setPurchaseItems(objectMapper.writeValueAsString(purchaseItems));
            } catch (JsonProcessingException e) {
                System.out.println("Error converting drugs to JSON");
                purchaseOrderEntity.setPurchaseItems(purchaseItems.toString());
            }
            purchaseOrderRepository.save(purchaseOrderEntity);
        } catch (Exception e) {
            return new ResponseEntity<>(new ProcurementResponse("99", "Purchase Order failed to create"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ProcurementResponse("00", "Purchase Order has been created"), HttpStatus.OK);
    }

    public ResponseEntity<ProcurementResponse> updatePurchaseOrder(UpdatePurchaseOrderDto updatePurchaseOrderDto) {
        try {
            Optional<PurchaseOrderEntity> existingOrder = purchaseOrderRepository.findByProcurementRequestId(updatePurchaseOrderDto.getProcurementRequestId());
            if (existingOrder.isEmpty()) {
                return new ResponseEntity<>(new ProcurementResponse("99", "Purchase Order for this procurement request does not exist"), HttpStatus.CREATED);
            }
            PurchaseOrderEntity existingOpt = existingOrder.get();
            if (updatePurchaseOrderDto.getHandledBy() != null) {
                existingOpt.setHandledBy(updatePurchaseOrderDto.getHandledBy());
            }
            if (updatePurchaseOrderDto.getOrderDate() != null) {
                existingOpt.setOrderDate(updatePurchaseOrderDto.getOrderDate());
            }
            if (updatePurchaseOrderDto.getExpectedDelivery() != null) {
                existingOpt.setExpectedDelivery(updatePurchaseOrderDto.getExpectedDelivery());
            }
            if (updatePurchaseOrderDto.getStatus() != null) {
                existingOpt.setStatus(updatePurchaseOrderDto.getStatus());
            }
            List<Items> purchaseItems = new ArrayList<>();
            for (UpdatePurchaseOrderDto.PurchaseItem item : updatePurchaseOrderDto.getPurchaseItems()) {
                Items orderItem = new Items();
                if (item.getItemId() != null) {
                    orderItem.setItemId(item.getItemId());
                }
                if (item.getItemType() != null) {
                    orderItem.setItemType(item.getItemType());
                }
                if (item.getQuantity() != null) {
                    orderItem.setQuantity(item.getQuantity());
                }
                purchaseItems.add(orderItem);
            }
            try {
                existingOpt.setPurchaseItems(objectMapper.writeValueAsString(purchaseItems));
            } catch (JsonProcessingException e) {
                System.out.println("Error converting drugs to JSON");
                existingOpt.setPurchaseItems(purchaseItems.toString());
            }
            purchaseOrderRepository.save(existingOpt);
        } catch (Exception e) {
            return new ResponseEntity<>(new ProcurementResponse("99", "Purchase Order failed to Update"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ProcurementResponse("00", "Purchase Order has been updated"), HttpStatus.OK);
    }

    public ResponseEntity<ProcurementResponse> getOrderByRequestId(Long procurementRequestId) {
        Optional<PurchaseOrderEntity> purchaseOrderEntity = purchaseOrderRepository.findByProcurementRequestId(procurementRequestId);
        if (purchaseOrderEntity.isEmpty()) {
            return new ResponseEntity<>(new ProcurementResponse("99", "Purchase order for this procurement request does not exist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ProcurementResponse("00", "Purchase Order for this request is :", purchaseOrderEntity), HttpStatus.OK);
    }

    public ResponseEntity<ProcurementResponse> getOrderByStatus(PurchaseOrderStatus status) {
        List<PurchaseOrderEntity> purchaseOrderEntities = purchaseOrderRepository.findByStatus(status);
        if (purchaseOrderEntities.isEmpty()) {
            return new ResponseEntity<>(new ProcurementResponse("99", "Purchase order with this status does not exist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ProcurementResponse("00", "Purchase order for this status are:", purchaseOrderEntities), HttpStatus.OK);
    }

    public ResponseEntity<ProcurementResponse> getOrderByHandler(String handledBy) {
        List<PurchaseOrderEntity> purchaseOrderEntityList = purchaseOrderRepository.findByHandler(handledBy);
        if (purchaseOrderEntityList.isEmpty()) {
            return new ResponseEntity<>(new ProcurementResponse("99", "Purchase Order for this handler could not be found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ProcurementResponse("00", "Purchase Order for this handler are:", purchaseOrderEntityList), HttpStatus.OK);
    }

    public ResponseEntity<ProcurementResponse> getOrderByDateRange(ProcurementDateDto procurementDateDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(procurementDateDto.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(procurementDateDto.getEndDate(), formatter);
        List<PurchaseOrderEntity> opt = purchaseOrderRepository.findByOrderDate(startDate, endDate);
        if (opt.isEmpty()) {
            return new ResponseEntity<>(new ProcurementResponse("99", "No available Purchased Order within this date range"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ProcurementResponse("00", "Purchased Order Found", opt), HttpStatus.OK);
    }

    public List<PurchaseOrderEntity> getAllOrders() {
        return purchaseOrderRepository.findAll();
    }

    public ResponseEntity<ProcurementResponse> deletePurchaseOrder(Long procurementRequestId) {
        purchaseOrderRepository.deleteByRequestId(procurementRequestId);
        return new ResponseEntity<>(new ProcurementResponse("00", "Purchase Order has been deleted successfully"), HttpStatus.OK);
    }
}



