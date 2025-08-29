package com.erpsoftware.inv_sup_management.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erpsoftware.inv_sup_management.entity.purchase_orders;

@Repository
public interface PurchaseOrdersRepository extends JpaRepository<purchase_orders,Integer>{}
