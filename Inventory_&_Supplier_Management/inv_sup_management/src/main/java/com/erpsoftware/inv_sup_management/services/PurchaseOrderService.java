package com.erpsoftware.inv_sup_management.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erpsoftware.inv_sup_management.entity.Product;
import com.erpsoftware.inv_sup_management.entity.Purchase_order_items;
import com.erpsoftware.inv_sup_management.entity.Purchase_orders;
import com.erpsoftware.inv_sup_management.repo.PurchaseOrderItemsRepository;
import com.erpsoftware.inv_sup_management.repo.PurchaseOrdersRepository;
import com.erpsoftware.inv_sup_management.services.Interfaces.ProductServicesInterface;
import com.erpsoftware.inv_sup_management.services.Interfaces.PurchaseOrdersServicesInterface;
import com.erpsoftware.inv_sup_management.services.Interfaces.StockServicesInterface;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.PurchaseOrder;

@Service
public class PurchaseOrderService implements PurchaseOrdersServicesInterface {

    private final StockServicesInterface stockService;
    private final ProductServicesInterface productService;
    private final PurchaseOrdersRepository purchaseOrdersRepository;
    private final PurchaseOrderItemsRepository purchaseOrderItemsRepository;
    public PurchaseOrderService(StockServicesInterface stockService,ProductServicesInterface productService,PurchaseOrdersRepository purchaseOrdersRepository,PurchaseOrderItemsRepository purchaseOrderItemsRepository){
        this.stockService = stockService;
        this.productService = productService;
        this.purchaseOrderItemsRepository = purchaseOrderItemsRepository;
        this.purchaseOrdersRepository = purchaseOrdersRepository;
    }

//--------------------------PURCHASE ORDERS

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
    public Purchase_orders savePurchaseOrder(Purchase_orders order){
        Purchase_orders created_order= purchaseOrdersRepository.save(order);
        return created_order;
    }


//-----------------PURCHASE ORDER ITEMS
    @Override
    public List<Purchase_order_items> getPurchaseOrderItems(Integer OrderID) {
        List<Purchase_order_items> order_items = purchaseOrderItemsRepository.findAllByOrderId(OrderID);
        return order_items;
    }

    @Override
    public Purchase_order_items getPurchaseOrderItem(Integer PurchaseOrderID){
        Purchase_order_items item = purchaseOrderItemsRepository.findById(PurchaseOrderID).orElseThrow(()->new RuntimeException("Not Found"));
        return item;
    }

    @Override
    @Transactional
    public PurchaseOrder createPurchaseOrderItems(Purchase_orders orders, List<Purchase_order_items> order_items) {
        Purchase_orders createdOrder = savePurchaseOrder(orders);
        for(Purchase_order_items item:order_items){
            item.setOrderId(createdOrder.getId());
        }
        List<Purchase_order_items> created_order_items = purchaseOrderItemsRepository.saveAll(order_items);
        return new PurchaseOrder(createdOrder,created_order_items);
    }

    @Override
    public Purchase_order_items updatePurchaseOrderItem(Purchase_order_items order_items) {
        Purchase_order_items item = purchaseOrderItemsRepository.save(order_items);
        return item;
    }

    @Override
    @Transactional
    public List<Purchase_order_items> receivePurchaseOrderItems(List<Purchase_order_items> order_items){
        Integer order_id = 0;
        List<Purchase_order_items> updatedList = new ArrayList<>();
        for(Purchase_order_items item:order_items){
            Purchase_order_items beforeItem = getPurchaseOrderItem(item.getId());
            Product product = productService.getProduct(item.getProductId());
            Integer beforeReceiveQty = beforeItem.getReceivedQuantity();
            Integer afterReceiveQty = item.getReceivedQuantity();
            Integer changesQty = afterReceiveQty - beforeReceiveQty;
            Purchase_order_items updatedItem = updatePurchaseOrderItem(item);
            product.setQuantity(product.getQuantity()+changesQty);
            productService.updateProduct(product,product.getId());
            stockService.addMovement(afterReceiveQty, changesQty, "Receive Purchase Item", order_id);
            updatedList.add(updatedItem);
            order_id = item.getOrderId();
        }
        Purchase_orders order = getPurchaseOrder(order_id);
        if(AllReceiveCheck(order_id)){
            order.setStatus("Completed");
            savePurchaseOrder(order);
        }else{
            order.setStatus("Partially Received");
            savePurchaseOrder(order);
        }
        return updatedList;
    }

    @Override
    public boolean AllReceiveCheck(Integer orderId){
        List<Purchase_order_items> order_items = purchaseOrderItemsRepository.findAllByOrderId(orderId);
        for(Purchase_order_items item:order_items){
            if(item.getQuantity() != item.getReceivedQuantity()){
                return false;
            }
        }
        return true;
    }
}
