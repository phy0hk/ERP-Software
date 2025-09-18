package com.erpsoftware.inv_sup_management.services.Interfaces;

import java.util.List;
import java.util.Locale.Category;

import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.entity.Locations;
import com.erpsoftware.inv_sup_management.entity.ProductCategory;
import com.erpsoftware.inv_sup_management.entity.Purchase_order_items;
import com.erpsoftware.inv_sup_management.entity.Purchase_orders;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.PurchaseOrder;

@Service
public interface PurchaseOrdersServicesInterface {
    List<Purchase_orders> getAllPurchaseOrders();
    Purchase_orders getPurchaseOrder(Integer id);
    Purchase_orders savePurchaseOrder(Purchase_orders order);

    List<Purchase_order_items> getPurchaseOrderItems(Integer OrderID);
    Purchase_order_items getPurchaseOrderItem(Integer purchaseOrderID);
    PurchaseOrder createPurchaseOrderItems(Purchase_orders orders,List<Purchase_order_items> order_items);
    Purchase_order_items updatePurchaseOrderItem(Purchase_order_items order_items);
    List<Purchase_order_items> receivePurchaseOrderItems(List<Purchase_order_items> order_item,Locations location);
    public boolean AllReceiveCheck(Integer orderId);
}
