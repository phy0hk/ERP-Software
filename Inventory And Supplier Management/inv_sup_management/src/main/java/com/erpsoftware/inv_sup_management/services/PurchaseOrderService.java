package com.erpsoftware.inv_sup_management.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erpsoftware.inv_sup_management.entity.Inventory;
import com.erpsoftware.inv_sup_management.entity.Locations;
import com.erpsoftware.inv_sup_management.entity.Product;
import com.erpsoftware.inv_sup_management.entity.Purchase_order_items;
import com.erpsoftware.inv_sup_management.entity.Purchase_orders;
import com.erpsoftware.inv_sup_management.repo.PurchaseOrderItemsRepository;
import com.erpsoftware.inv_sup_management.repo.PurchaseOrdersRepository;
import com.erpsoftware.inv_sup_management.security.ApiException;
import com.erpsoftware.inv_sup_management.services.Interfaces.InventoryServicesInterface;
import com.erpsoftware.inv_sup_management.services.Interfaces.LocationServicesInterface;
import com.erpsoftware.inv_sup_management.services.Interfaces.ProductServicesInterface;
import com.erpsoftware.inv_sup_management.services.Interfaces.PurchaseOrdersServicesInterface;
import com.erpsoftware.inv_sup_management.services.Interfaces.StockServicesInterface;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.PurchaseOrder;

@Service
public class PurchaseOrderService implements PurchaseOrdersServicesInterface {

    private final StockServicesInterface stockService;
    private final ProductServicesInterface productService;
    private final InventoryServicesInterface inventoryServices;
    private final LocationServicesInterface locationServices;
    private final PurchaseOrdersRepository purchaseOrdersRepository;
    private final PurchaseOrderItemsRepository purchaseOrderItemsRepository;

    public PurchaseOrderService(StockServicesInterface stockService, ProductServicesInterface productService,
            PurchaseOrdersRepository purchaseOrdersRepository,
            PurchaseOrderItemsRepository purchaseOrderItemsRepository,
            InventoryServicesInterface inventoryServices,
            LocationServicesInterface locationServices) {
        this.stockService = stockService;
        this.productService = productService;
        this.purchaseOrderItemsRepository = purchaseOrderItemsRepository;
        this.purchaseOrdersRepository = purchaseOrdersRepository;
        this.inventoryServices = inventoryServices;
        this.locationServices = locationServices;
    }

    // --------------------------PURCHASE ORDERS

    @Override
    public List<Purchase_orders> getAllPurchaseOrders() {
        List<Purchase_orders> purchase_orders = purchaseOrdersRepository.findAll();
        return purchase_orders;
    }

    @Override
    public Purchase_orders getPurchaseOrder(Integer id) {
        Purchase_orders purchase_orders = purchaseOrdersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        return purchase_orders;
    }

    @Override
    public Purchase_orders savePurchaseOrder(Purchase_orders order) {
        Purchase_orders created_order = purchaseOrdersRepository.save(order);
        return created_order;
    }

    // -----------------PURCHASE ORDER ITEMS
    @Override
    public List<Purchase_order_items> getPurchaseOrderItems(Integer OrderID) {
        List<Purchase_order_items> order_items = purchaseOrderItemsRepository.findAllByOrderId(OrderID);
        return order_items;
    }

    @Override
    public Purchase_order_items getPurchaseOrderItem(Integer PurchaseOrderID) {
        Purchase_order_items item = purchaseOrderItemsRepository.findById(PurchaseOrderID)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        return item;
    }

    @Override
    @Transactional
    public PurchaseOrder createPurchaseOrderItems(Purchase_orders orders, List<Purchase_order_items> order_items) {
        Purchase_orders createdOrder = savePurchaseOrder(orders);
        for (Purchase_order_items item : order_items) {
            item.setOrderId(createdOrder.getId());
        }
        List<Purchase_order_items> created_order_items = purchaseOrderItemsRepository.saveAll(order_items);
        return new PurchaseOrder(createdOrder, created_order_items);
    }

    @Override
    public Purchase_order_items updatePurchaseOrderItem(Purchase_order_items order_items) {
        Purchase_order_items item = purchaseOrderItemsRepository.save(order_items);
        return item;
    }

    @Override
    @Transactional
    public List<Purchase_order_items> receivePurchaseOrderItems(List<Purchase_order_items> order_items,
            Locations location) {
        Integer order_id = order_items.getFirst().getOrderId();
        Purchase_orders order = getPurchaseOrder(order_id);
        List<Purchase_order_items> updatedList = new ArrayList<>();
        if(order.getStatus().equals("Completed")){
            throw new ApiException("You cant receive completed order", 400);
        }
        for (Purchase_order_items item : order_items) {
            if (item.getQuantity() < item.getReceivedQuantity()) {
                throw new ApiException("Receive quantity can't be exceed purchase request quantity",400);
            }
            Integer receiveQty = item.getReceivedQuantity();
            item = purchaseOrderItemsRepository.findById(item.getId()).orElse(null);
            Integer beforeReceiveQty = item.getReceivedQuantity();
            Inventory inventoryItem = inventoryServices.getInventory(location.getId(), item.getProductId());
            if (inventoryItem == null) {
                inventoryItem = new Inventory();
                inventoryItem.setLocationId(location.getId());
                inventoryItem.setProductId(item.getProductId());
                inventoryItem.setQuantity(0);
                inventoryItem.setReserved(0);
            }
            System.out.println("Before quantity : "+beforeReceiveQty +" After quantity : "+receiveQty);
            if(beforeReceiveQty >= receiveQty){
                throw new ApiException("Receive quantity can't be less than or equal previous receive quantity",400);
            }
            item.setReceivedQuantity(receiveQty);
            Integer changesQty = receiveQty - beforeReceiveQty;
            Purchase_order_items updatedItem = updatePurchaseOrderItem(item);
            inventoryItem.setQuantity(inventoryItem.getQuantity() + changesQty);
            System.out.println("Changed Inventory Item "+ inventoryItem);
            inventoryServices.saveInventory(inventoryItem);
            stockService.addMovement(inventoryItem.getProductId(), changesQty, "Receive Purchase Item",
                    "INV"+inventoryItem.getId());
            updatedList.add(updatedItem);
        }
        if (AllReceiveCheck(order_id)) {
            order.setStatus("Completed");
            savePurchaseOrder(order);
        } else {
            order.setStatus("Partially Received");
            savePurchaseOrder(order);
        }
        return updatedList;
    }

    @Override
    public boolean AllReceiveCheck(Integer orderId) {
        List<Purchase_order_items> order_items = purchaseOrderItemsRepository.findAllByOrderId(orderId);
        for (Purchase_order_items item : order_items) {
            if (item.getQuantity() != item.getReceivedQuantity()) {
                return false;
            }
        }
        return true;
    }
}
