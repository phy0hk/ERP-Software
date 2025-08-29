package com.erpsoftware.inv_sup_management.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "stock_movements")
public class stock_movements {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private int product_id;
    @Column
    private int change;
    @Column
    private String reason;
    @Column
    private int source_id;
    @Column
    private Timestamp created_at;

    public int getChange() {
        return change;
    }
    public Timestamp getCreated_at() {
        return created_at;
    }
    public int getId() {
        return id;
    }
    public int getProduct_id() {
        return product_id;
    }
    public String getReason() {
        return reason;
    }
    public int getSource_id() {
        return source_id;
    }
}
