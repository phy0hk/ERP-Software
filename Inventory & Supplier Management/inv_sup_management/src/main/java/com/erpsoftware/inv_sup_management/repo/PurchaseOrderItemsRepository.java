package com.erpsoftware.inv_sup_management.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erpsoftware.inv_sup_management.entity.Purchase_order_items;

public interface PurchaseOrderItemsRepository extends JpaRepository<Purchase_order_items,Integer> {}
