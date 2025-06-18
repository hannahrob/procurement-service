package com.example.procurement_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcurementResponse {
    private String responseCode;
    private String responseMessage;
    private RequestResponseBody responseBody;

    public ProcurementResponse(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.responseBody = null;
    }
}
