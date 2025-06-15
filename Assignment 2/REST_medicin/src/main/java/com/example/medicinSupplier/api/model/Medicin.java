package com.example.medicinSupplier.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medicins")

public class Medicin {

    @Id
    @Column(name = "medicinid")
    private String medicinId;
    @Column
    private int stock;
    @Column
    private String title;
    @Column
    private String description;


    public Medicin() {}

    public Medicin(String medicinId, int stock, String title, String description) {
        this.medicinId = medicinId;
        this.stock = stock;
        this.title = title;
        this.description = description;
    }


    public String getMedicinId() {
        return medicinId;
    }

    public void setMedicinId(String medicinId) {
        this.medicinId = medicinId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
