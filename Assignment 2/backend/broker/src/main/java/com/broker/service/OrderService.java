package com.broker.service;

import com.broker.dto.CreateOrderDTO;
import com.broker.entity.Item;
import com.broker.entity.Order;
import com.broker.repository.OrderRepository;
import com.broker.dto.Supplier1PurchaseResponseDTO;
import com.broker.dto.Supplier2PurchaseResponseDTO;
import com.broker.service.supplier.Supplier;
import com.broker.service.supplier.Supplier1Service;
import com.broker.service.supplier.Supplier2Service;
import com.broker.service.supplier.WorkingSupplierMockup;
import lombok.Data;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {
    private static final String ORDERSTATUS_ONGOING = "ongoing";
    private static final String ORDERSTATUS_SUCCEEDED = "succeeded";
    private static final String ORDERSTATUS_FAILED = "failed";

    private static final String SUPPLIERSTATUS_NOANSWER = "noanswer";
    private static final String SUPPLIERSTATUS_RESERVED = "reserved";
    private static final String SUPPLIERSTATUS_DECLINED = "declined";


    private final OrderRepository orderRepository;
    private final ItemService itemService; // TODO: This only has 1 usage, maybe consider getting rid of it
    private final Supplier supplier1;


    public OrderService(OrderRepository orderRepository, ItemService itemService, WorkingSupplierMockup supplier1) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
        this.supplier1 = supplier1;
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

    public Order createOrder(CreateOrder createOrder) {
        Item item = this.itemService.getItemById(createOrder.getItemId()).orElse(null);

        if (item == null) {
            throw new NoSuchElementException("Can't create order as requested item " + createOrder.getItemId() + " doesn't exist.");
        }

        Order newOrder = new Order();
        newOrder.setDeliveryAddress(createOrder.getDeliveryAddress());
        newOrder.setItem(item);
        newOrder.setUserId(createOrder.getUserId());
        newOrder.setStatus(ORDERSTATUS_ONGOING);
        newOrder.setSupplier1Status(SUPPLIERSTATUS_NOANSWER);
        newOrder.setSupplier2Status(SUPPLIERSTATUS_NOANSWER);
        newOrder.setTime(LocalDateTime.now());

        newOrder = orderRepository.save(newOrder);
        return newOrder;
    }

    @Data
    public static class CreateOrder {
        private String userId;
        private int itemId;
        private String deliveryAddress;
    }
}