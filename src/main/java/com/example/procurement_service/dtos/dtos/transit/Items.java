package com.example.procurement_service.dtos.dtos.transit;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Items {
    private String itemId;
    private String itemType;
    private Integer quantity;
}
