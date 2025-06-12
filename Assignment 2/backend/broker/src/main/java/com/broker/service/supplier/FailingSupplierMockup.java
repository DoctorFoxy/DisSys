package com.broker.service.supplier;

import org.springframework.stereotype.Service;

@Service
public class FailingSupplierMockup implements Supplier {

    @Override
    public boolean prepareReservation(int reservationId, String itemId, int quantity) throws TimeoutException {
        throw new TimeoutException();
    }

    @Override
    public void commitReservation(int reservationId) throws TimeoutException {

    }

    @Override
    public void abortReservation(int reservationId) throws TimeoutException {

    }
}
