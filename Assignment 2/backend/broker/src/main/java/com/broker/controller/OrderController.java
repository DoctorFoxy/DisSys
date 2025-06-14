package com.broker.controller;

import com.broker.entity.Order;
import com.broker.service.OrderService;
import com.broker.dto.CreateOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // GET /api/orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // POST /api/orders
    @PostMapping
    public Order createOrder(@RequestBody CreateOrderDTO dto, @AuthenticationPrincipal Jwt token) {
        String userId = token.getSubject();

        var newOrder = new OrderService.CreateOrder();
        newOrder.setDeliveryAddress(dto.getDeliveryAddress());
        newOrder.setItemId(dto.getItemId());
        newOrder.setUserId(userId);
        newOrder.setSimulateNoFinalizationMessage(dto.isSimulateNoFinalizationMessage());

        return orderService.createOrder(newOrder);
    }

    // GET /api/orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/orders/user
    @GetMapping("/user")
    public List<Order> getOrderByUserId(@AuthenticationPrincipal Jwt token) {
        String userId = token.getSubject();
        return orderService.getOrderByUserId(userId);
    }

    // GET /api/orders/{id}/status
    @GetMapping("/status/{id}")
    public ResponseEntity<String> getOrderStatus(@PathVariable UUID id) {
        this.logger.info("status poll endpoint called for {}", id);
        return orderService.getOrderStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
