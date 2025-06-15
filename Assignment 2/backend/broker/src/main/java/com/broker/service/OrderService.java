package com.broker.service;

import com.broker.entity.Item;
import com.broker.entity.Order;
import com.broker.repository.OrderRepository;
import com.broker.service.supplier.Supplier;
import com.broker.service.supplier.Supplier1Service;
import com.broker.service.supplier.Supplier2Service;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private static final String ORDERSTATUS_ONGOING = "ongoing";
    private static final String ORDERSTATUS_SUCCEEDED = "succeeded";
    private static final String ORDERSTATUS_FAILED = "failed";

    private static final String SUPPLIERSTATUS_NOANSWER = "noanswer";
    private static final String SUPPLIERSTATUS_RESERVED = "reserved";
    private static final String SUPPLIERSTATUS_DECLINED = "declined";

    @Value("${broker.order.timeout-minutes}")
    private int ORDER_TIMEOUT_DURATION_MINUTES;


    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final Supplier supplier1;
    private final Supplier supplier2;


    public OrderService(OrderRepository orderRepository, ItemService itemService, Supplier1Service supplier1, Supplier2Service supplier2) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
        this.supplier1 = supplier1;
        this.supplier2 = supplier2;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(UUID id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrderByUserId(String user_id) {
        return orderRepository.findAllByUserId(user_id);
    }

    public Optional<String> getOrderStatusById(UUID id) {
        return orderRepository.findStatusById(id);
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

        Order ongoingOrder = orderRepository.save(newOrder);

        Order processedOrder = this.processOngoingOrder(ongoingOrder, createOrder.isSimulateNoFinalizationMessage());
        return processedOrder;
    }

    @Scheduled(fixedRateString = "${broker.order.retry-interval-ms}")
    public void processOngoingOrders() {
        // Check for any ongoing orders and process them
        List<Order> ongoingOrders = orderRepository.findAllByStatus(ORDERSTATUS_ONGOING);
        System.out.println("Retrying " + ongoingOrders.size() + " ongoing orders.");
        for (Order ongoingOrder : ongoingOrders) {
            processOngoingOrder(ongoingOrder, false);
        }
    }

    private synchronized Order processOngoingOrder(Order order, boolean simulateNoFinalizationMessage) {

        // If order is expired, then abort
        LocalDateTime expiresAt = order.getTime().plusMinutes(ORDER_TIMEOUT_DURATION_MINUTES);
        boolean isExpired = LocalDateTime.now().isAfter(expiresAt);
        if(isExpired) {
            return this.abortOrder(order, simulateNoFinalizationMessage);
        }


        // Check if supplier1 has answered and if not, try to reserve and react to the response
        boolean s1_isNoAnswer = order.getSupplier1Status().equals(SUPPLIERSTATUS_NOANSWER);
        if (s1_isNoAnswer) {
            // Try to reserve supplier1
            try {
                boolean reservation_success = supplier1.prepareReservation(
                        order.getId(),
                        order.getItem().getSupplier1ItemId(),
                        order.getItem().getSupplier1ItemQuantity());

                if (reservation_success) {
                    // Mark supplier1 as reserved
                    order.setSupplier1Status(SUPPLIERSTATUS_RESERVED);


                    // If all suppliers are now reserved, then commit order
                    boolean s1_isReserved = order.getSupplier1Status().equals(SUPPLIERSTATUS_RESERVED);
                    boolean s2_isReserved = order.getSupplier2Status().equals(SUPPLIERSTATUS_RESERVED);
                    if (s1_isReserved && s2_isReserved) {
                        return this.commitOrder(order, simulateNoFinalizationMessage);
                    } else {  // otherwise, save in DB that supplier1 is reserved
                        order = orderRepository.save(order);
                    }
                } else {
                    // Abort order
                    order.setSupplier1Status(SUPPLIERSTATUS_DECLINED);
                    return this.abortOrder(order, simulateNoFinalizationMessage);
                }
            } catch (Supplier.TimeoutException e) {
                // If supplier doesn't respond, do nothing as this function will be recalled later
            }
        }


        // Same logic again for supplier2
        // Check if supplier2 has answered and if not, try to reserve and react to the response
        boolean s2_isNoAnswer = order.getSupplier2Status().equals(SUPPLIERSTATUS_NOANSWER);
        if (s2_isNoAnswer) {
            // Try to reserve supplier2
            try {
                boolean reservation_success = supplier2.prepareReservation(
                        order.getId(),
                        order.getItem().getSupplier2ItemId(),
                        order.getItem().getSupplier2ItemQuantity());

                if (reservation_success) {
                    // Mark supplier2 as reserved
                    order.setSupplier2Status(SUPPLIERSTATUS_RESERVED);


                    // If all suppliers are now reserved, then commit order
                    boolean s1_isReserved = order.getSupplier1Status().equals(SUPPLIERSTATUS_RESERVED);
                    boolean s2_isReserved = order.getSupplier2Status().equals(SUPPLIERSTATUS_RESERVED);
                    if (s1_isReserved && s2_isReserved) {
                        return this.commitOrder(order, simulateNoFinalizationMessage);
                    } else {  // otherwise, save in DB that supplier2 is reserved
                        order = orderRepository.save(order);
                    }
                } else {
                    // Abort order
                    order.setSupplier2Status(SUPPLIERSTATUS_DECLINED);
                    return this.abortOrder(order, simulateNoFinalizationMessage);
                }
            }
            catch (Supplier.TimeoutException e) {
                // If supplier doesn't respond, do nothing as this function will be recalled later
            }
        }

        return order;
    }

    private Order commitOrder(Order order, boolean simulateNoFinalizationMessage) {
        // Save in DB first
        order.setStatus(ORDERSTATUS_SUCCEEDED);
        order = orderRepository.save(order);


        // Notify suppliers
        if(simulateNoFinalizationMessage) {
            return order;
        }

        try {
            supplier1.commitReservation(order.getId());
        } catch (Supplier.TimeoutException e) {
            // Our policy is to not care if the supplier doesn't respond to commit/abort
        }

        try {
            supplier2.commitReservation(order.getId());
        } catch (Supplier.TimeoutException e) {
            // Our policy is to not care if the supplier doesn't respond to commit/abort
        }



        return order;
    }

    private Order abortOrder(Order order, boolean simulateNoFinalizationMessage) {
        // Save in DB first
        order.setStatus(ORDERSTATUS_FAILED);
        order = orderRepository.save(order);

        // Notify suppliers
        if(simulateNoFinalizationMessage) {
            return order;
        }

        try {
            supplier1.abortReservation(order.getId());
        } catch (Supplier.TimeoutException e) {
            // Our policy is to not care if the supplier doesn't respond to commit/abort
        }

        try {
            supplier2.abortReservation(order.getId());
        } catch (Supplier.TimeoutException e) {
            // Our policy is to not care if the supplier doesn't respond to commit/abort
        }

        return order;
    }

    @Data
    public static class CreateOrder {
        private String userId;
        private int itemId;
        private String deliveryAddress;
        private boolean simulateNoFinalizationMessage;
    }
}