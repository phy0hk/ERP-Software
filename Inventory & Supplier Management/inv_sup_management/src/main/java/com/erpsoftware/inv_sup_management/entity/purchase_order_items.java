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
@Table(name = "purchase_order_items")
public class Purchase_order_items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer order_id;
    @Column
    private Integer product_id;
    @Column
    private Integer quantity;
    @Column
    private double price;
    @Column
    private Integer received_quantity;

    public Integer getId() {
        return id;
    }
    public Integer getOrder_id() {
        return order_id;
    }
    public double getPrice() {
        return price;
    }
    public Integer getProduct_id() {
        return product_id;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public Integer getReceived_quantity() {
        return received_quantity;
    }
}