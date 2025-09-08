package com.erpsoftware.inv_sup_management.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.erpsoftware.inv_sup_management.entity.Purchase_order_items;
import com.erpsoftware.inv_sup_management.entity.Purchase_orders;
import com.erpsoftware.inv_sup_management.security.AuthGuard;
import com.erpsoftware.inv_sup_management.services.Interfaces.PurchaseOrdersServicesInterface;
import com.erpsoftware.inv_sup_management.services.Interfaces.StockServicesInterface;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.PurchaseOrder;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.StatusResponder;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@AuthGuard
@RequestMapping("/api/v1")
public class PurchaseController {

    private final StockServicesInterface stockService;
    private final PurchaseOrdersServicesInterface purchaseOrderService;

    public PurchaseController(StockServicesInterface stockService,PurchaseOrdersServicesInterface purchaseOrderSerivce){
        this.stockService = stockService;
        this.purchaseOrderService = purchaseOrderSerivce;
    }

    @GetMapping("/purchase/{purchaseID}")
    public StatusResponder<PurchaseOrder> getPurchaseOrder(@PathVariable Integer purchaseID) {
        List<Purchase_order_items> order_items = purchaseOrderService.getPurchaseOrderItems(purchaseID);
        Purchase_orders order = purchaseOrderService.getPurchaseOrder(purchaseID);
        PurchaseOrder purchaseOrder = new PurchaseOrder(order,order_items);
        return new StatusResponder<>("ok",purchaseOrder);
    }

    @PostMapping("/purchase/newOrder")
    public StatusResponder<PurchaseOrder> newPurchaseOrder(@RequestBody PurchaseOrder entity) {
        PurchaseOrder newOrder = purchaseOrderService.createPurchaseOrderItems(entity.order(),entity.order_items());
        return new StatusResponder<>("ok",newOrder);
    }
    
    
    
    
}
