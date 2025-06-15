package com.example.medicinSupplier.api.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @Column(name = "reservationid")
    private String reservationId;
    @Column(name = "medicinid")
    private String medicinId;
    @Column
    private int quantity;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column
    private LocalDateTime timestamp;
    @Column
    private String brokerEndpoint;

    public enum Status {
        RESERVED,
        FINALIZED,
        ABORTED
    }

    @PrePersist
    @PreUpdate
    protected void updateTimestamp() {
        this.timestamp = LocalDateTime.now();
    }

    public Reservation() {}

    public Reservation(String reservationId, String medicinId, int quantity, Status status, LocalDateTime timestamp, String brokerEndpoint) {
        this.reservationId = reservationId;
        this.medicinId = medicinId;
        this.quantity = quantity;
        this.status = status;
        this.timestamp = timestamp;
        this.brokerEndpoint = brokerEndpoint;
    }

    public Reservation(String reservationId, String medicinId, int quantity, Status status, LocalDateTime timestamp) {
        this.reservationId = reservationId;
        this.medicinId = medicinId;
        this.quantity = quantity;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getReservationId() {return reservationId;}

    public void setReservationId(String reservationId) {this.reservationId = reservationId;}

    public String getMedicinId() {return medicinId;}

    public void setMedicinId(String medicinId) {this.medicinId = medicinId;}

    public int getQuantity() {return quantity;}

    public void setQuantity(int quantity) {this.quantity = quantity;}

    public Status getStatus() {return status;}

    public void setStatus(Status status) {this.status = status;}

    public String getBroker() {return brokerEndpoint;}

    public void setBroker(String brokerEndpoint) {this.brokerEndpoint = brokerEndpoint;}

    public LocalDateTime getTimestamp() {return timestamp;}

}