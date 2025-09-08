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
@Table(name = "suppliers")
public class Suppliers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String contact_name;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String email;
    @Column
    private String address;
    @Column
    private String notes;
    @CreationTimestamp
    @Column(insertable = false)
    private Timestamp created_at; 
    @UpdateTimestamp
    @Column(insertable = false)
    private Timestamp updated_at; 

    
}
