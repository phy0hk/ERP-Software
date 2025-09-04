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
    @Column(nullable = false)
    private Integer order_id;
    @Column(nullable = false)
    private Integer product_id;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private Integer received_quantity;
}