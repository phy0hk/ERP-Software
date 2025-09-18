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
@Table(name = "purchase_orders")
public class Purchase_orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "supplier_id",nullable = false)
    private Integer supplierId;
    @Column(nullable = false)
    private String status;
    @CreationTimestamp
    @Column(insertable = false)
    private Timestamp created_at;
    @UpdateTimestamp
    @Column(insertable = false)
    private Timestamp updated_at;
}
