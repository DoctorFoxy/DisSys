package com.broker.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private String deliveryAddress;
    private String status;
    private String userId;
    private Integer itemId;
}