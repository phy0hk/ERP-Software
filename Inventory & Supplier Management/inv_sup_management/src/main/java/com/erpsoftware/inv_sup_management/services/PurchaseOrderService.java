package com.erpsoftware.inv_sup_management.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.entity.Purchase_order_items;
import com.erpsoftware.inv_sup_management.entity.Purchase_orders;
import com.erpsoftware.inv_sup_management.repo.PurchaseOrderItemsRepository;
import com.erpsoftware.inv_sup_management.repo.PurchaseOrdersRepository;
import com.erpsoftware.inv_sup_management.services.Interfaces.PurchaseOrdersServicesInterface;
import com.erpsoftware.inv_sup_management.services.Interfaces.StockServicesInterface;

@Service
public class PurchaseOrderService implements PurchaseOrdersServicesInterface {

    private final StockServicesInterface stockService;
    private final PurchaseOrdersRepository purchaseOrdersRepository;
    private final PurchaseOrderItemsRepository purchaseOrderItemsRepository;
    public PurchaseOrderService(StockServicesInterface stockService,PurchaseOrdersRepository purchaseOrdersRepository,PurchaseOrderItemsRepository purchaseOrderItemsRepository){
        this.stockService = stockService;
        this.purchaseOrderItemsRepository = purchaseOrderItemsRepository;
        this.purchaseOrdersRepository = purchaseOrdersRepository;
    }



    @Override
    public List<Purchase_orders> getAllPurchaseOrders() {
        List<Purchase_orders> purchase_orders = purchaseOrdersRepository.findAll();
        return purchase_orders;
    }

    @Override
    public Purchase_orders getPurchaseOrder(Integer id) {
        Purchase_orders purchase_orders = purchaseOrdersRepository.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
        return purchase_orders;
    }

    @Override
    public List<Purchase_order_items> getPurchaseOrderItems(Integer OrderID) {
        List<Purchase_order_items> order_items = purchaseOrderItemsRepository.findAllByOrder_ID(OrderID);
        return order_items;
    }

    @Override
    public String createPurchaseOrderItems(Purchase_orders orders, List<Purchase_order_items> order_items) {
        Purchase_orders createdOrder = purchaseOrdersRepository.save(orders);
        for(Purchase_order_items item:order_items){
            item.setOrder(createdOrder);
        }
        purchaseOrderItemsRepository.saveAll(order_items);
        return "ok";
    }

    @Override
    public String updatePurchaseOrderItem(Purchase_order_items order_items) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePurchaseOrderItem'");
    }
    
}
