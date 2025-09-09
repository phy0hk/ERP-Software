package com.erpsoftware.inv_sup_management.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.erpsoftware.inv_sup_management.entity.Purchase_order_items;
import com.erpsoftware.inv_sup_management.entity.Purchase_orders;
import com.erpsoftware.inv_sup_management.security.AuthGuard;
import com.erpsoftware.inv_sup_management.services.Interfaces.PurchaseOrdersServicesInterface;
import com.erpsoftware.inv_sup_management.services.Interfaces.StockServicesInterface;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.PurchaseOrder;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.ReceiveOrderBody;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.StatusResponder;

import java.util.ArrayList;
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
        try {
            List<Purchase_order_items> order_items = purchaseOrderService.getPurchaseOrderItems(purchaseID);
            Purchase_orders order = purchaseOrderService.getPurchaseOrder(purchaseID);
            PurchaseOrder purchaseOrder = new PurchaseOrder(order,order_items);
            return new StatusResponder<>("ok",purchaseOrder);
        } catch (Exception e) {
            return new StatusResponder<>("bad",null);
        }
    }

    @GetMapping("/purchase/getAll/details")
    public StatusResponder<List<PurchaseOrder>> getAllPurchaseOrderDetails() {
        try {
            List<PurchaseOrder> purchaseOrders = new ArrayList<>();
            List<Purchase_orders> orders = purchaseOrderService.getAllPurchaseOrders();
            for(Purchase_orders order:orders){
                List<Purchase_order_items> order_items = purchaseOrderService.getPurchaseOrderItems(order.getId());
                purchaseOrders.add(new PurchaseOrder(order,order_items));
            }
            return new StatusResponder<>("ok",purchaseOrders);
        } catch (Exception e) {
            return new StatusResponder<>("bad",null);
        }
    }
    @GetMapping("/purchase/getAll")
    public StatusResponder<List<Purchase_orders>> getAllPurchaseOrder() {
        try {
            List<Purchase_orders> orders = purchaseOrderService.getAllPurchaseOrders();
            return new StatusResponder<>("ok",orders);
        } catch (Exception e) {
            return new StatusResponder<>("bad",null);
        }
    }

    @PostMapping("/purchase/newOrder")
    public StatusResponder<PurchaseOrder> newPurchaseOrder(@RequestBody PurchaseOrder entity) {
        try {
            PurchaseOrder newOrder = purchaseOrderService.createPurchaseOrderItems(entity.order(),entity.order_items());
            return new StatusResponder<>("ok",newOrder);
        } catch (Exception e) {
            return new StatusResponder<>("bad",null);
        }
    }
    
    @PostMapping("/purchase/receive")
    public StatusResponder<List<Purchase_order_items>> receiveOrder(@RequestBody ReceiveOrderBody entity) {
            List<Purchase_order_items> items = purchaseOrderService.receivePurchaseOrderItems(entity.order_items(), entity.location());
            return new StatusResponder<>("ok",items);
        

    }
    
    
}
