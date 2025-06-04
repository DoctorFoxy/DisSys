package com.broker.controller;

import com.broker.entity.Item;
import com.broker.entity.Order;
import com.broker.service.ItemService;
import com.broker.service.OrderService;
import com.broker.dto.OrderRequestDTO;
import org.springframework.http.ResponseEntity;
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

    // POST /api/orders
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setDeliveryAddress(orderRequestDTO.getDeliveryAddress());
        order.setStatus(orderRequestDTO.getStatus());
        order.setUserId(orderRequestDTO.getUserId());

        // fetch item from DB using itemId
        Item item = itemService.getItemById(orderRequestDTO.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        order.setItem(item);

        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(savedOrder);
    }
}
