package com.erpsoftware.inv_sup_management.services.Interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.entity.Purchase_order_items;
import com.erpsoftware.inv_sup_management.entity.Purchase_orders;

@Service
public interface PurchaseOrdersServicesInterface {
    List<Purchase_orders> getAllPurchaseOrders();
    Purchase_orders getPurchaseOrder(Integer id);
    List<Purchase_order_items> getPurchaseOrderItems(Integer purchaseOrderID);
    String createPurchaseOrderItems(Purchase_orders orders,List<Purchase_order_items> order_items);
    String updatePurchaseOrderItem(Purchase_order_items order_items);
}
