package com.erpsoftware.inv_sup_management.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String sku;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer category_id;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private Double cost_price;
    @Column(nullable = false)
    private Double selling_price;
    @Column(nullable = false)
    private Integer low_stock_threshold;
    @CreationTimestamp
    @Column(insertable = false)
    private Timestamp created_at;
    @UpdateTimestamp
    @Column(insertable = false)
    private Timestamp updated_at;
}
