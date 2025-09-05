package com.erpsoftware.inv_sup_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "purchase_order_items")
public class Purchase_order_items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Purchase_orders order;
    @Column(nullable = false)
    private Integer product_id;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private Integer received_quantity;
}