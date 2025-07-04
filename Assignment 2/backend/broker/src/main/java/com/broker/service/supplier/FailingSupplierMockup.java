package com.broker.service.supplier;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FailingSupplierMockup implements Supplier {

    @Override
    public boolean prepareReservation(UUID reservationId, String itemId, int quantity) throws TimeoutException {
        throw new TimeoutException("Error during prepareReservation: ");
    }

    @Override
    public void commitReservation(UUID reservationId) throws TimeoutException {

    }

    @Override
    public void abortReservation(UUID reservationId) throws TimeoutException {

    }
}
