package com.broker.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;


@Entity
@Table(name = "items")
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(name = "supplier1_item_id")
    private String supplier1ItemId;

    @Column(name = "supplier1_item_quantity")
    private Integer supplier1ItemQuantity;

    @Column(name = "supplier2_item_id")
    private String supplier2ItemId;

    @Column(name = "supplier2_item_quantity")
    private Integer supplier2ItemQuantity;

    @Column
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_link")
    private String imageLink;
}
