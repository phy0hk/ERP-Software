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
@Table(name = "suppliers")
public class Suppliers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String contact_name;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private String address;
    @Column 
    private String notes;
    @Column(insertable = false)
    private Timestamp created_at; 
    @Column(insertable = false)
    private Timestamp updated_at; 

    
    public String getAddress() {
        return address;
    }
    public String getContact_name() {
        return contact_name;
    }
    public Timestamp getCreated_at() {
        return created_at;
    }
    public String getEmail() {
        return email;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getNotes() {
        return notes;
    }
    public String getPhone() {
        return phone;
    }
    public Timestamp getUpdated_at() {
        return updated_at;
    }
}
