package com.broker.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    private String status;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "supplier1_status")
    private String supplier1Status;

    @Column(name = "supplier2_status")
    private String supplier2Status;

    @Column(name = "time")
    private LocalDateTime time;
}
