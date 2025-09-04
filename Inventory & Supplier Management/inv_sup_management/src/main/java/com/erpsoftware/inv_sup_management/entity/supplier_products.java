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
    @Column(nullable = false)
    private Integer supplier_id;
    @Column(nullable = false)
    private Integer product_id;
    @Column(nullable = false)
    private String supplier_sku;
    @Column(nullable = false)
    private double cost_price;
    @Column(nullable = false)
    private Integer lead_time_days;
}
