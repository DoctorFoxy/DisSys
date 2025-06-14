package com.broker.service.supplier;

import org.springframework.stereotype.Service;

@Service
public class WorkingSupplierMockup implements Supplier {

    @Override
    public boolean prepareReservation(int reservationId, String itemId, int quantity) throws TimeoutException {
        return true;
    }

    @Override
    public void commitReservation(int reservationId) throws TimeoutException {

    }

    @Override
    public void abortReservation(int reservationId) throws TimeoutException {

    }
}
