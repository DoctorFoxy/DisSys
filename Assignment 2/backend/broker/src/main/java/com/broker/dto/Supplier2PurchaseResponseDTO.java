package com.broker.dto;

public class Supplier2PurchaseResponseDTO {
    private String message;        // for success or info (e.g., "Reservation committed")
    private String error;          // for failure

    public boolean isSuccess() {
        return message != null && (
                message.toLowerCase().contains("successful") ||
                        message.toLowerCase().contains("committed") ||
                        message.toLowerCase().contains("prepared") ||
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