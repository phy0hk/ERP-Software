package com.erpsoftware.inv_sup_management.entity;

import java.sql.Timestamp;

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
@Table(name = "inventory")
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "location_id")
    private Integer locationId;
    @Column
    private Integer quantity;
    @Column
    private Integer reserved;
    @UpdateTimestamp
    @Column(insertable = false)
    private Timestamp last_update;
}