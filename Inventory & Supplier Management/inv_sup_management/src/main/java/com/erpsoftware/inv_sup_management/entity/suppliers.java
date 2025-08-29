package com.erpsoftware.inv_sup_management.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class suppliers {
    @Id
    @GeneratedValue
    private int id;
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
    @Column 
    private Timestamp created_at; 
    @Column 
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
    public int getId() {
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
