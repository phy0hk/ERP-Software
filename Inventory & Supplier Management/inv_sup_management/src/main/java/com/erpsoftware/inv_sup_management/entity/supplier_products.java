package com.erpsoftware.inv_sup_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "supplier_products")
public class Supplier_products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer supplier_id;
    @Column
    private Integer product_id;
    @Column
    private String supplier_sku;
    @Column
    private double cost_price;
    @Column
    private Integer lead_time_days;

    public double getCost_price() {
        return cost_price;
    }
    public Integer getId() {
        return id;
    }
    public Integer getLead_time_days() {
        return lead_time_days;
    }
    public Integer getProduct_id() {
        return product_id;
    }
    public Integer getSupplier_id() {
        return supplier_id;
    }
    public String getSupplier_sku() {
        return supplier_sku;
    }
}
