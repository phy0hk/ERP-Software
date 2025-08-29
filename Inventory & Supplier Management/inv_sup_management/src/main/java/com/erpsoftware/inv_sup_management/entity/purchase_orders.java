package com.erpsoftware.inv_sup_management.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class purchase_orders {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private int supplier_id;
    @Column
    private String status;
    @Column
    private Timestamp created_at;
    @Column
    private Timestamp updated_at;
    
    public Timestamp getCreated_at() {
        return created_at;
    }
    public int getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }
    public int getSupplier_id() {
        return supplier_id;
    }
    public Timestamp getUpdated_at() {
        return updated_at;
    }
}
