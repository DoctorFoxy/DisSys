package com.broker.controller.debug;
//REMOVE AT THE END
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.broker.service.supplier.Supplier1Service;
import com.broker.service.supplier.Supplier2Service;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierDebugController {
    private final Supplier1Service supplier1Service;
    private final Supplier2Service supplier2Service;

    public SupplierDebugController(Supplier1Service s1, Supplier2Service s2) {
        this.supplier1Service = s1;
        this.supplier2Service = s2;
    }

    @GetMapping("/ping")
    public String ping() {
        System.out.println("🔔 PING");
        return "pong";
    }

    // --- TEST Supplier 1 ---
    @PostMapping("/supplier1/prepare/{orderId}/{itemId}/{quantity}")
    public ResponseEntity<?> debugPrepareS1(@PathVariable int orderId, @PathVariable String itemId, @PathVariable int quantity) {
        System.out.printf("➡️ Preparing Supplier1 reservation: orderId=%d, itemId=%s, quantity=%d%n", orderId, itemId, quantity);
        try {
            boolean result = supplier1Service.prepareReservation(orderId, itemId, quantity);
            return ResponseEntity.ok("Prepare result: " + result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/supplier1/commit/{orderId}")
    public ResponseEntity<?> debugCommitS1(@PathVariable int orderId) {
        try {
            supplier1Service.commitReservation(orderId);
            return ResponseEntity.ok("Commit succeeded");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Commit error: " + e.getMessage());
        }
    }

    @PostMapping("/supplier1/abort/{orderId}")
    public ResponseEntity<?> debugAbortS1(@PathVariable int orderId) {
        try {
            supplier1Service.abortReservation(orderId);
            return ResponseEntity.ok("Abort succeeded");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Abort error: " + e.getMessage());
        }
    }

    @PostMapping("/supplier2/prepare/{orderId}/{itemId}/{quantity}")
    public ResponseEntity<?> debugPrepareS2(
            @PathVariable int orderId,
            @PathVariable String itemId,
            @PathVariable int quantity) {
        System.out.printf("➡️ Preparing Supplier2 reservation: orderId=%d, itemId=%s, quantity=%d%n", orderId, itemId, quantity);
        try {
            boolean result = supplier2Service.prepareReservation(orderId, itemId, quantity);
            return ResponseEntity.ok("Prepare result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/supplier2/commit/{orderId}")
    public ResponseEntity<?> debugCommitS2(@PathVariable int orderId) {
        try {
            supplier2Service.commitReservation(orderId);
            return ResponseEntity.ok("Commit succeeded");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Commit error: " + e.getMessage());
        }
    }

    @PostMapping("/supplier2/abort/{orderId}")
    public ResponseEntity<?> debugAbortS2(@PathVariable int orderId) {
        try {
            supplier2Service.abortReservation(orderId);
            return ResponseEntity.ok("Abort succeeded");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Abort error: " + e.getMessage());
        }
    }

}