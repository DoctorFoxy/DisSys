package com.broker.service;

import com.broker.entity.Item;
import com.broker.entity.Order;
import com.broker.repository.OrderRepository;
import com.broker.dto.Supplier1PurchaseResponseDTO;
import com.broker.dto.Supplier2PurchaseResponseDTO;
import com.broker.service.supplier.Supplier1Service;
import com.broker.service.supplier.Supplier2Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final Supplier1Service supplier1Service;
    private final Supplier2Service supplier2Service;
    private final ItemService itemService;
    @Value("${broker.order.timeout-minutes:15}")
    private int orderTimeoutMinutes;

    public OrderService(OrderRepository orderRepository, Supplier1Service supplier1Service, Supplier2Service supplier2Service, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.supplier1Service = supplier1Service;
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

            String failReason = (supplier2Response != null && supplier2Response.isError())
                    ? supplier2Response.getError()
                    : "Unknown error or no response";

            System.out.println("Supplier2 purchase failed: " + failReason);
        }

        return orderRepository.save(savedOrder);
    }

    @Async
    @Transactional
    public CompletableFuture<Order> placeFullBrokerOrderAsync(String userId, String deliveryAddress, Integer itemId) {
        return CompletableFuture.completedFuture(
                placeFullBrokerOrderWithTimeout(userId, deliveryAddress, itemId)
        );
    }

    @Transactional
    public Order placeFullBrokerOrderWithTimeout(String userId, String deliveryAddress, Integer itemId) {
        Item item = itemService.getItemById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Order order = new Order();
        order.setUserId(userId);
        order.setDeliveryAddress(deliveryAddress);
        order.setItem(item);
        order.setStatus("ongoing");
        order.setSupplier1Status("pending");
        order.setSupplier2Status("pending");
        order = orderRepository.save(order);

        int reservationId = order.getId(); // Order ID for reservation

        Duration timeout = Duration.ofMinutes(orderTimeoutMinutes);
        Instant start = Instant.now();

        boolean supplier1Ready = false;
        boolean supplier2Ready = false;

        // Supplier 1 reservation loop
//        while (Duration.between(start, Instant.now()).compareTo(timeout) < 0) {
//            var response = supplier1Service.prepareReservation(
//                    reservationId,
//                    Integer.parseInt(item.getSupplier1ItemId()),
//                    item.getSupplier1ItemQuantity());
//
//            if (response != null && response.isSuccess()) {
//                supplier1Ready = true;
//                break;
//            }
//
//            try {
//                Thread.sleep(60000); // Wait 1 minute
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                break;
//            }
//        }
//
//        if (!supplier1Ready) {
//            supplier1Service.abortReservation(reservationId);
//            order.setSupplier1Status("failed");
//            order.setStatus("failed");
//            return orderRepository.save(order);
//        }
//
//        order.setSupplier1Status("complete");
//        orderRepository.save(order); // persist intermediate state

        // === MOCK Supplier 1 === because supplier one not working yet
        supplier1Ready = true; // pretend it's ready
        order.setSupplier1Status("complete");
        orderRepository.save(order);
        // =======================

        // Supplier 2 reservation loop
        while (Duration.between(start, Instant.now()).compareTo(timeout) < 0) {
            Supplier2PurchaseResponseDTO supplier2Response = supplier2Service.prepareReservation(
                    reservationId,
                    Integer.parseInt(item.getSupplier2ItemId()),
                    item.getSupplier2ItemQuantity());

            if (supplier2Response != null && supplier2Response.isSuccess()) {
                supplier2Ready = true;
                break;
            }

            try {
                Thread.sleep(60000); // Wait 1 minute
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        if (!supplier2Ready) {
            supplier2Service.abortReservation(reservationId);
//            supplier1Service.abortReservation(reservationId); // rollback supplier 1
            order.setSupplier2Status("failed");
            order.setSupplier1Status("rolledback");
            order.setStatus("failed");
            return orderRepository.save(order);
        }

        Supplier2PurchaseResponseDTO supplier2CommitResponse = supplier2Service.commitReservation(reservationId);
        if (supplier2CommitResponse != null && supplier2CommitResponse.isSuccess()) {
//            boolean supplier1Commit = supplier1Service.finalizeReservation(reservationId).isSuccess();
            boolean supplier1Commit = true; // mock success ////////////////////////////////////////////////////////////
            if (supplier1Commit) {
                order.setSupplier2Status("complete");
                order.setStatus("succeeded");
            } else {
                supplier2Service.abortReservation(reservationId);
                order.setSupplier1Status("failed");
                order.setSupplier2Status("rolledback");
                order.setStatus("failed");
            }
        } else {
//            supplier1Service.abortReservation(reservationId);
            order.setSupplier2Status("failed");
            order.setSupplier1Status("rolledback");
            order.setStatus("failed");
        }
        return orderRepository.save(order);
    }
}