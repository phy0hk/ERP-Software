package com.erpsoftware.inv_sup_management.utils;

import java.util.List;

import com.erpsoftware.inv_sup_management.entity.Inventory;
import com.erpsoftware.inv_sup_management.entity.Locations;
import com.erpsoftware.inv_sup_management.entity.Purchase_order_items;
import com.erpsoftware.inv_sup_management.entity.Purchase_orders;

public class ResponseJson {
    public record StatusResponder<T>(String status, T data) {}
    public record PurchaseOrder(Purchase_orders order,List<Purchase_order_items> order_items){};
    public record ReceiveOrderBody(List<Purchase_order_items> order_items,Locations location){};
    public record MoveInvItem(Inventory item,Locations location){};
    public record MoveInvItemMulti(List<Inventory> items,Locations prevlocation,Locations newLocation){};
    public record AdjustStock(Integer product_id,String reason,String reportedBy,String sourceRef,Integer quantity,Integer locationId,Integer adjustType) {}
}
