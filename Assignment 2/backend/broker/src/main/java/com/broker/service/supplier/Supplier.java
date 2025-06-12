package com.broker.service.supplier;

import com.broker.dto.Supplier1PurchaseResponseDTO;

public interface Supplier {

    boolean prepareReservation(int reservationId, String itemId, int quantity);

    void commitReservation(int reservationId);

    void abortReservation(int reservationId);
}
