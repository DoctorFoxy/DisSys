package com.broker.entity;

import jakarta.persistence.*;
import lombok.Data;

//import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    private String status;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

//    @Column(name = "time")
//    private LocalDateTime time;
}
