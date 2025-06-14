package com.broker.dto;

public class Supplier1PurchaseResponseDTO {
    private final String rawResponse;

    public Supplier1PurchaseResponseDTO(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public boolean isSuccess() {
        return rawResponse != null && rawResponse.toLowerCase().contains("ack");
    }

    public boolean isError() {
        return rawResponse != null && rawResponse.toLowerCase().contains("nack");
    }

    public String getRawResponse() {
        return rawResponse;
    }
}