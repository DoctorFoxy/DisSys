package com.broker.controller;

import com.broker.entity.Item;
import com.broker.entity.Order;
import com.broker.service.ItemService;
import com.broker.service.OrderService;
import com.broker.dto.OrderRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final ItemService itemService;

    public OrderController(OrderService orderService, ItemService itemService) {
        this.orderService = orderService;
        this.itemService = itemService;
    }

    // GET /api/orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // GET /api/orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
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

    // POST /api/orders
    @PostMapping("/placeFullOrder")
    public ResponseEntity<Order> placeFullOrder(@RequestBody OrderRequestDTO dto) {
        Order order = orderService.placeFullBrokerOrder(dto.getUserId(), dto.getDeliveryAddress(), dto.getItemId());
        return ResponseEntity.ok(order);
    }

    // POST /api/orders/placeFullOrderAsync
    @PostMapping("/placeFullOrderAsync")
    public ResponseEntity<String> placeFullOrderAsync(@RequestBody OrderRequestDTO dto) {
        orderService.placeFullBrokerOrderAsync(dto.getUserId(), dto.getDeliveryAddress(), dto.getItemId());
        return ResponseEntity.accepted().body("Order is being processed asynchronously.");
    }
}
