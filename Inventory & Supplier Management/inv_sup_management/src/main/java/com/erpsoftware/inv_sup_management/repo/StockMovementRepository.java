package com.erpsoftware.inv_sup_management.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erpsoftware.inv_sup_management.entity.stock_movements;

@Repository
public interface StockMovementRepository extends JpaRepository<stock_movements,Integer>{}
