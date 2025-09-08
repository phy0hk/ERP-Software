package com.erpsoftware.inv_sup_management.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "damage_reports")
public class Damage_Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer damage_id;
    @Column(nullable = false)
    private Integer product_id;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private String reason;
    @Column(nullable = false)
    private String reported_by;
    @Column(nullable = false)
    private String source_ref;
    @Column(insertable = false)
    private Timestamp created_at;
}