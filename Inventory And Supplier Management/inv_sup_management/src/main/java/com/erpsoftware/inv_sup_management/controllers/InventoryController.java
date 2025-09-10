package com.erpsoftware.inv_sup_management.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.erpsoftware.inv_sup_management.entity.Inventory;
import com.erpsoftware.inv_sup_management.security.AuthGuard;
import com.erpsoftware.inv_sup_management.services.Interfaces.InventoryServicesInterface;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.MoveInvItem;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.MoveInvItemMulti;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.StatusResponder;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@AuthGuard
@RequestMapping("/api/v1")
public class InventoryController {
    
    private final InventoryServicesInterface inventoryServices;
    public InventoryController(InventoryServicesInterface inventoryServices){
        this.inventoryServices = inventoryServices;
    }


    @GetMapping("/inventory/getAll")
    public StatusResponder<List<Inventory>> getInventoryList() {
        try {
            List<Inventory> items = inventoryServices.getAllInventories();
            return new StatusResponder<>("ok",items);
        } catch (Exception e) {
            return new StatusResponder<>("bad",null);
        }
    }


    @GetMapping("/inventory/getAll/location")
    public StatusResponder<List<Inventory>> getInventoryListByLocationId(@RequestParam Integer id) {
        try {
            List<Inventory> items = inventoryServices.getAllItemsByLocation(id);
            return new StatusResponder<>("ok",items);
        } catch (Exception e) {
            return new StatusResponder<>("bad",null);
        }
    }


    @GetMapping("/inventory/getAll/product")
    public StatusResponder<List<Inventory>> getInventoryListByProductId(@RequestParam Integer id) {
        try {
            List<Inventory> items = inventoryServices.getAllItemsByProduct(id);
            return new StatusResponder<>("ok",items);
        } catch (Exception e) {
            return new StatusResponder<>("bad",null);
        }
    }

    @PutMapping("/inventory/movement")
    public StatusResponder<Inventory> moveInventoryItem(@RequestBody MoveInvItem entity) {
        Inventory item = inventoryServices.moveInvItem(entity);
        return new StatusResponder<>("ok",item);
    }
    @PutMapping("/inventory/movement/multi")
    public StatusResponder<List<Inventory>> moveInventoryItem(@RequestBody MoveInvItemMulti entity) {
        List<Inventory> items = inventoryServices.moveInvItems(entity);
        return new StatusResponder<>("ok",items);
    }
}
