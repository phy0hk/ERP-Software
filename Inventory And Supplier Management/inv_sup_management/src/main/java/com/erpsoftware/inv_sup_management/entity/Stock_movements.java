package com.erpsoftware.inv_sup_management.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "stock_movements")
public class Stock_movements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer product_id;
    @Column(nullable = false)
    private Integer change;
    @Column(nullable = false)
    private String reason;
    @Column(nullable = false)
    private String source_id;
    @CreationTimestamp
    @Column(insertable = false)
    private Timestamp created_at;

}
