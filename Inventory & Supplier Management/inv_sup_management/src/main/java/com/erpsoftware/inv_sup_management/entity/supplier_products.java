package com.erpsoftware.inv_sup_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "supplier_products")
public class supplier_products {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private int supplier_id;
    @Column
    private int product_id;
    @Column
    private String supplier_sku;
    @Column
    private double cost_price;
    @Column
    private int lead_time_days;

    public double getCost_price() {
        return cost_price;
    }
    public int getId() {
        return id;
    }
    public int getLead_time_days() {
        return lead_time_days;
    }
    public int getProduct_id() {
        return product_id;
    }
    public int getSupplier_id() {
        return supplier_id;
    }
    public String getSupplier_sku() {
        return supplier_sku;
    }
}
