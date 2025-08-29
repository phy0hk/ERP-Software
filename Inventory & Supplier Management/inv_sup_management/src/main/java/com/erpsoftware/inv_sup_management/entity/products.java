package com.erpsoftware.inv_sup_management.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class products {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String sku;
    @Column
    private String name;
    @Column
    private String category;
    @Column
    private String brand;
    @Column
    private Double cost_price;
    @Column
    private Double selling_price;
    @Column
    private int quantity;
    @Column
    private int low_stock_threshold;
    @Column
    private Timestamp created_at;
    @Column
    private Timestamp updated_at;

    // public String getBrand() {
    //     return brand;
    // }
    // public String getCategory() {
    //     return category;
    // }
    // public Double getCost_price() {
    //     return cost_price;
    // }
    // public Timestamp getCreated_at() {
    //     return created_at;
    // }
    // public int getId() {
    //     return id;
    // }
    // public int getLow_stock_threshold() {
    //     return low_stock_threshold;
    // }
    // public String getName() {
    //     return name;
    // }
    // public int getQuantity() {
    //     return quantity;
    // }
    // public Double getSelling_price() {
    //     return selling_price;
    // }
    // public String getSku() {
    //     return sku;
    // }
    // public Timestamp getUpdated_at() {
    //     return updated_at;
    // }
}
