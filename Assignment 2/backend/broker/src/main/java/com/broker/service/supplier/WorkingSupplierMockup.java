package com.broker.service.supplier;

import org.springframework.stereotype.Service;

@Service
public class WorkingSupplierMockup implements Supplier {

    @Override
    public boolean prepareReservation(int reservationId, int itemId, int quantity) {
        return false;
    }

    @Override
    public void commitReservation(int reservationId) {

    }

    @Override
    public void abortReservation(int reservationId) {

    }
}
