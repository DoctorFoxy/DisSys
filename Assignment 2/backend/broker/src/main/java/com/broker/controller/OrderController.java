package com.broker.controller;

import com.broker.entity.Order;
import com.broker.service.ItemService;
import com.broker.service.OrderService;
import com.broker.dto.CreateOrderDTO;
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

    // POST /api/orders
    @PostMapping
    public Order createOrder(@RequestBody CreateOrderDTO dto, @AuthenticationPrincipal Jwt token) {
        String userId = token.getSubject();

        var newOrder = new OrderService.CreateOrder();
        newOrder.setDeliveryAddress(dto.getDeliveryAddress());
        newOrder.setItemId(dto.getItemId());
        newOrder.setUserId(userId);

        return orderService.createOrder(newOrder);
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
}
