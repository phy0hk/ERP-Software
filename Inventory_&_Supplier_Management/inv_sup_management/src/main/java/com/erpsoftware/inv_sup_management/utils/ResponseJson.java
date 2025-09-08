package com.erpsoftware.inv_sup_management.utils;

import java.util.List;

import com.erpsoftware.inv_sup_management.entity.Purchase_order_items;
import com.erpsoftware.inv_sup_management.entity.Purchase_orders;

public class ResponseJson {
    public record StatusResponder<T>(String status, T data) {}
    public record PurchaseOrder(Purchase_orders order,List<Purchase_order_items> order_items){};
}
