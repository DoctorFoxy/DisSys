package com.broker.service.supplier;

public interface Supplier {

    boolean prepareReservation(int reservationId, String itemId, int quantity) throws TimeoutException;

    void commitReservation(int reservationId) throws TimeoutException;

    void abortReservation(int reservationId) throws TimeoutException;

    /**
     * Thrown, when the supplier doesn't respond.
     */
    class TimeoutException extends Exception {
        public TimeoutException(String message) {
            super(message);
        }
    }
}
