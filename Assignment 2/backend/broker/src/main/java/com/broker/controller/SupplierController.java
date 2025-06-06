package com.broker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.broker.service.supplier.Supplier1Service;
import com.broker.service.supplier.Supplier2Service;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final Supplier1Service supplier1Service;
    private final Supplier2Service supplier2Service;

    public SupplierController(Supplier1Service s1, Supplier2Service s2) {
        this.supplier1Service = s1;
        this.supplier2Service = s2;
    }

    @GetMapping("/supplier1/items/{id}")
    public ResponseEntity<String> getSupplier1Item(@PathVariable Integer id) {
        return ResponseEntity.ok(supplier1Service.getSupplierItemById(id));
    }

    @GetMapping("/supplier2/items/{id}")
    public ResponseEntity<String> getSupplier2Item(@PathVariable Integer id) {
        return ResponseEntity.ok(supplier2Service.getSupplierItemById(id));
    }

    @GetMapping("/supplier2/item")
    public ResponseEntity<String> getSupplier2Items() {
        return ResponseEntity.ok(supplier2Service.getSupplierItems());
    }
}