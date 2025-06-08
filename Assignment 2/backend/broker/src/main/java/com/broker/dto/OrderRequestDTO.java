package com.broker.dto;

public class OrderRequestDTO {
    private String userId;
    private String deliveryAddress;
    private Integer itemId;

    public OrderRequestDTO() {}

    public OrderRequestDTO(String userId, String deliveryAddress, Integer itemId) {
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.itemId = itemId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}