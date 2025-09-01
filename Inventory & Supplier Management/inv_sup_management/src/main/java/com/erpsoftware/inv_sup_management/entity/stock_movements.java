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
@Table(name = "stock_movements")
public class Stock_movements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer product_id;
    @Column
    private Integer change;
    @Column
    private String reason;
    @Column
    private Integer source_id;
    @Column(insertable = false)
    private Timestamp created_at;

    public Integer getChange() {
        return change;
    }
    public Timestamp getCreated_at() {
        return created_at;
    }
    public Integer getId() {
        return id;
    }
    public Integer getProduct_id() {
        return product_id;
    }
    public String getReason() {
        return reason;
    }
    public Integer getSource_id() {
        return source_id;
    }
}
