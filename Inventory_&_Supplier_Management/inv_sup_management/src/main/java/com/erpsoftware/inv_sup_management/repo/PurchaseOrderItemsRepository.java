package com.erpsoftware.inv_sup_management.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erpsoftware.inv_sup_management.entity.Purchase_order_items;

@Repository
public interface PurchaseOrderItemsRepository extends JpaRepository<Purchase_order_items,Integer> {
    List<Purchase_order_items> findAllByOrderId(Integer orderId);
}
