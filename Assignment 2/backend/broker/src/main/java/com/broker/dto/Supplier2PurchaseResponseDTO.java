package com.broker.dto;

public class Supplier2PurchaseResponseDTO {
    private String message;       // for success
    private Integer remaining_stock;

    private String error;         // for failure

    public boolean isSuccess() {
        return message != null && message.equalsIgnoreCase("Purchase successful");
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Integer getRemaining_stock() { return remaining_stock; }
    public void setRemaining_stock(Integer remaining_stock) { this.remaining_stock = remaining_stock; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}