package com.broker.dto;

public class Supplier1PurchaseResponseDTO {
    private String message; // e.g., "Reservation prepared", "Already finalized", etc.
    private String error;   // for failure messages, if any

    public boolean isSuccess() {
        return message != null && (
                message.toLowerCase().contains("prepared") ||
                        message.toLowerCase().contains("finalized") ||
                        message.toLowerCase().contains("already")
        );
    }

    public boolean isError() {
        return error != null && !error.isBlank();
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}
