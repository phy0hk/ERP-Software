package com.erpsoftware.inv_sup_management.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.entity.Inventory;
import com.erpsoftware.inv_sup_management.entity.Locations;
import com.erpsoftware.inv_sup_management.repo.InventoryRepository;
import com.erpsoftware.inv_sup_management.security.ApiAuthException;
import com.erpsoftware.inv_sup_management.services.Interfaces.InventoryServicesInterface;
import com.erpsoftware.inv_sup_management.services.Interfaces.LocationServicesInterface;
import com.erpsoftware.inv_sup_management.services.Interfaces.StockServicesInterface;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.MoveInvItem;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.MoveInvItemMulti;

import jakarta.transaction.Transactional;

@Service
public class InventoryServices implements InventoryServicesInterface {

    private final InventoryRepository inventoryRepository;
    private final StockServicesInterface stockServices;
    private final LocationServicesInterface locationServices;
    public InventoryServices(InventoryRepository inventoryRepository,StockServicesInterface stockServices,LocationServicesInterface locationServices){
        this.inventoryRepository = inventoryRepository;
        this.stockServices = stockServices;
        this.locationServices = locationServices;
    }

    @Override
    public List<Inventory> getAllInventories() {
        List<Inventory> items = inventoryRepository.findAll();
        return items;
    }

    @Override
    public List<Inventory> getAllItemsByLocation(Integer id) {
        List<Inventory> items = inventoryRepository.findAllByLocationId(id);
        return items;
    }

    @Override
    public List<Inventory> getAllItemsByProduct(Integer id) {
        List<Inventory> items = inventoryRepository.findAllByProductId(id);
        return items;
    }

    @Override
    public Inventory getInventory(Integer id) {
        Inventory item = inventoryRepository.findById(id).orElse(null);
        return item;
    }

    @Override
    public Inventory getInventory(Integer locationId,Integer productId) {
        Inventory item = inventoryRepository.findByLocationIdAndProductId(locationId,productId).orElse(null);
        return item;
    }

    @Override
    public Inventory saveInventory(Inventory inventory) {
        Inventory updatedItem = inventoryRepository.save(inventory);
        return updatedItem;
    }

    @Override
    @Transactional
    public Inventory moveInvItem(MoveInvItem entity) {
        Inventory movedItem = entity.item();
        Locations prevLocation = locationServices.getLocationInfo(movedItem.getLocationId());
        Locations newLocation = entity.location();
        movedItem.setLocationId(newLocation.getId());
        stockServices.addMovement(movedItem.getProductId(), 0, "Move item from "+prevLocation.getCode()+" "+newLocation.getCode()+".", "INV"+movedItem.getId());
        inventoryRepository.save(movedItem);
        return movedItem;
    }

    @Override
    @Transactional
    public List<Inventory> moveInvItems(MoveInvItemMulti items) {
        List<Inventory> invItems = items.items();
        Locations prevLocation = items.prevlocation();
        Locations newLocation = items.newLocation();
        for(Inventory invItem:invItems){
            invItem.setLocationId(newLocation.getId());
            stockServices.addMovement(invItem.getProductId(), 0, "Move item from "+prevLocation.getCode()+" "+newLocation.getCode()+".", "INV"+invItem.getId());
        }
        inventoryRepository.saveAll(invItems);
        return invItems;
    }
    
}
