package com.example.medicinSupplier.service;

import com.example.medicinSupplier.api.model.Reservation;
import com.example.medicinSupplier.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MedicinService medicinService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, MedicinService medicinService) {
        this.reservationRepository = reservationRepository;
        this.medicinService = medicinService;
    }

    @Transactional
    public String getStatus(String reservationId) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);

        if (reservationOpt.isEmpty()) {
            return "Reservation not found";
        }
        Reservation reservation = reservationOpt.get();
        return reservation.getStatus().toString();
    }

    @Transactional
    public boolean prepareReservation(String reservationId, String medicinId, int quantity) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            return reservation.getStatus() == Reservation.Status.RESERVED;
        }

        boolean reserved = medicinService.reserveStock(medicinId, quantity);
        if (!reserved) {
            return false;
        }
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);
        reservation.setMedicinId(medicinId);
        reservation.setQuantity(quantity);
        reservation.setStatus(Reservation.Status.RESERVED);
        reservationRepository.save(reservation);

        return true;
    }

    @Transactional
    public boolean prepareReservationwithBroker(String reservationId, String medicinId, int quantity, String brokerEndpoint) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            return reservation.getStatus() == Reservation.Status.RESERVED;
        }

        boolean reserved = medicinService.reserveStock(medicinId, quantity);
        if (!reserved) {
            return false;
        }
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);
        reservation.setMedicinId(medicinId);
        reservation.setQuantity(quantity);
        reservation.setStatus(Reservation.Status.RESERVED);
        reservation.setBroker(brokerEndpoint);
        reservationRepository.save(reservation);

        return true;
    }

    @Transactional
    public boolean finalizeReservation(String reservationId) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);

        if (reservationOpt.isEmpty()) {
            return false;
        }

        Reservation reservation = reservationOpt.get();

        if (reservation.getStatus() != Reservation.Status.RESERVED) {
            return false;
        }
        reservation.setStatus(Reservation.Status.FINALIZED);
        reservationRepository.save(reservation);
        return true;
    }

    @Transactional
    public boolean abortReservation(String reservationId) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);

        if (reservationOpt.isEmpty()) {
            return false;
        }

        Reservation reservation = reservationOpt.get();

        if (reservation.getStatus() != Reservation.Status.RESERVED) {
            return false;
        }
        medicinService.updateStock(reservation.getMedicinId(), reservation.getQuantity());
        reservation.setStatus(Reservation.Status.ABORTED);
        reservationRepository.save(reservation);
        return true;
    }

    @Transactional
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public void resetReservations() {
        reservationRepository.deleteAll();
    }
}
