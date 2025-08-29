package com.erpsoftware.inv_sup_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity(name = "purchase_order_items")
public class purchase_order_items {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private int order_id;
    @Column
    private int product_id;
    @Column
    private int quantity;
    @Column
    private double price;
    @Column
    private int received_quantity;

    public int getId() {
        return id;
    }
    public int getOrder_id() {
        return order_id;
    }
    public double getPrice() {
        return price;
    }
    public int getProduct_id() {
        return product_id;
    }
    public int getQuantity() {
        return quantity;
    }
    public int getReceived_quantity() {
        return received_quantity;
    }
}