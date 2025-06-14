package com.broker.service.supplier;

import java.util.UUID;

public interface Supplier {

    boolean prepareReservation(UUID reservationId, String itemId, int quantity) throws TimeoutException;

    void commitReservation(UUID reservationId) throws TimeoutException;

    void abortReservation(UUID reservationId) throws TimeoutException;

    /**
     * Thrown, when the supplier doesn't respond.
     */
    class TimeoutException extends Exception {
        public TimeoutException(String message) {
            super(message);
        }
    }
}
