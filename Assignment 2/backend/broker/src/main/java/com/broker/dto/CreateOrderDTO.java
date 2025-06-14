package com.broker.dto;

import lombok.Data;

@Data
public class CreateOrderDTO {
    private String deliveryAddress;
    private Integer itemId;
    private boolean simulateNoFinalizationMessage;
}