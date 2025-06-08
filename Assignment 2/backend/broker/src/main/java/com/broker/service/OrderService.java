package com.broker.service;

import com.broker.entity.Item;
import com.broker.entity.Order;
import com.broker.repository.OrderRepository;
import com.broker.dto.Supplier1PurchaseResponseDTO;
import com.broker.dto.Supplier2PurchaseResponseDTO;
import com.broker.service.supplier.Supplier1Service;
import com.broker.service.supplier.Supplier2Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final Supplier2Service supplier2Service;
    private final ItemService itemService;

    public OrderService(OrderRepository orderRepository, Supplier2Service supplier2Service, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.supplier2Service = supplier2Service;
        this.itemService = itemService;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrderByUserId(String user_id) {
        return orderRepository.findAllByUserId(user_id);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public Order placeFullBrokerOrder(String userId, String deliveryAddress, Integer itemId) {
        Item item = itemService.getItemById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Order order = new Order();
        order.setUserId(userId);
        order.setDeliveryAddress(deliveryAddress);
        order.setItem(item);
        order.setStatus("ongoing");
        order.setSupplier1Status("pending");
        order.setSupplier2Status("pending");
        Order savedOrder = orderRepository.save(order);

        // Simulate supplier 1 order (print only for now)
        System.out.println("Placing order with Supplier 1: itemId=" + item.getSupplier1ItemId() + ", quantity=" + item.getSupplier1ItemQuantity());
        savedOrder.setSupplier1Status("complete"); // simulate success

        // Real supplier 2 call
        Supplier2PurchaseResponseDTO supplier2Response =
                supplier2Service.buyItemWithQuantity(item.getSupplier2ItemId(), item.getSupplier2ItemQuantity());

        if (supplier2Response != null && supplier2Response.isSuccess()) {
            savedOrder.setSupplier2Status("complete");
            savedOrder.setStatus("succeeded");
        } else {
            savedOrder.setSupplier2Status("failed");
            savedOrder.setStatus("failed");
            System.out.println("Supplier2 purchase failed: " +
                    (supplier2Response != null ? supplier2Response.getError() : "Unknown error"));
        }

        return orderRepository.save(savedOrder);
    }
}