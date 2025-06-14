package com.broker.dto;

import java.util.UUID;

public class Supplier2PurchaseResponseDTO {
    private UUID reservation_id = null; // On a successful prepare, the supplier answers with a reservation_id
    private String message;        // for success or info (e.g., "Reservation committed")
    private String error;          // for failure

    public boolean isSuccess() {
        return reservation_id != null // If it has been changed, then it was included in the response => successful prepare
                || message != null && (
                message.toLowerCase().contains("successful") ||
                        message.toLowerCase().contains("committed") ||
                        message.toLowerCase().contains("prepared") ||
                        message.toLowerCase().contains("already")
        );
    }

    public boolean isError() {
        return error != null
                && !error.isBlank()
                && !error.equals("Not enough stock"); // Fuck you maarten, this is not an error
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getError() { return error; }

    public void setError(String error) { this.error = error; }

    public UUID getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(UUID reservation_id) {
        this.reservation_id = reservation_id;
    }
}