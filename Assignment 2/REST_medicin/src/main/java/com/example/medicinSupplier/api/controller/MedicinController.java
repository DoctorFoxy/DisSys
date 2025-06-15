package com.example.medicinSupplier.api.controller;

import com.example.medicinSupplier.api.model.Medicin;
import com.example.medicinSupplier.api.model.Reservation;
import com.example.medicinSupplier.service.MedicinService;
import com.example.medicinSupplier.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class MedicinController {
    private final MedicinService medicinService;
    private final ReservationService reservationService;

    @Autowired
    public MedicinController(ReservationService reservationService, MedicinService medicinService) {
        this.reservationService = reservationService;
        this.medicinService = medicinService;
    }
    @GetMapping("/api/medicins/all")
    public List<String> getAllMedicinIds() {
        return medicinService.getAllMedicinIds();
    }

    @GetMapping("/api/medicins/{id}")
    public Optional<Medicin> getMedicin(@PathVariable String id) {
        return medicinService.getMedicin(id);
    }

    @GetMapping("/api/medicins/status/{reservationId}")
    public String decision(@PathVariable String reservationId) {
        String status = reservationService.getStatus(reservationId);
        if ("Reservation not found".equals(status)) {
            return "Not found";
        }
        return status;
    }

    @PostMapping("/api/medicins/prepare/{reservationId}/{medicinId}/{quantity}")
    public ResponseEntity<String> prepareReservation(@PathVariable String reservationId, @PathVariable String medicinId, @PathVariable int quantity, @RequestParam(required = false) String brokerEndpoint) {
        boolean result;
        if (brokerEndpoint != null) {
            result = reservationService.prepareReservationwithBroker(reservationId, medicinId, quantity, brokerEndpoint);
        } else {
            result = reservationService.prepareReservation(reservationId, medicinId, quantity);
        }


        if (result) {
            return ResponseEntity.ok("ACK: " + reservationId);
        } else {
            return ResponseEntity.status(409).body("NACK: Insufficient stock");
        }
    }

    @PostMapping("/api/medicins/finalize/{reservationId}")
    public ResponseEntity<String> finalizeReservation(@PathVariable String reservationId) {
        boolean result = reservationService.finalizeReservation(reservationId);
        
        if (result) {
            return ResponseEntity.ok("ACK: " + reservationId);
        } else {
            return ResponseEntity.status(404).body("NACK: Reservation not found or invalid status");
        }
    }

    @PostMapping("/api/medicins/abort/{reservationId}")
    public ResponseEntity<String> abortReservation(@PathVariable String reservationId) {
        boolean result = reservationService.abortReservation(reservationId);
        if (result) {
            return ResponseEntity.ok("ACK: " + reservationId);
        } else {
            return ResponseEntity.status(404).body("NACK: Reservation not found or invalid status");
        }
    }

//    @PostMapping("/api/medicins/status/{reservationId}")
//    public ResponseEntity<String> decision(@PathVariable String reservationId) {
//        String status = reservationService.getStatus(reservationId);
//        if ("Reservation not found".equals(status)) {
//            return ResponseEntity.status(404).body(status);
//        }
//        return ResponseEntity.ok("ACK: " + reservationId+ " "+status );
//    }


    @PostMapping("/test/update-stock/{medicinId}/{quantity}")
    public void updateStock(@PathVariable String medicinId, @PathVariable int quantity){
        medicinService.updateStock(medicinId, quantity);
    }

    @GetMapping("/test/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/test/reset-stock")
    public ResponseEntity<Void> resetStock() {
        medicinService.resetStock();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/clear-reservations")
    public ResponseEntity<Void> clearReservations() {
        reservationService.resetReservations();
        return ResponseEntity.ok().build();
    }
}
