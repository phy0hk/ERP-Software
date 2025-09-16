package com.erpsoftware.inv_sup_management.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.entity.Inventory;
import com.erpsoftware.inv_sup_management.entity.Locations;
import com.erpsoftware.inv_sup_management.repo.InventoryRepository;
import com.erpsoftware.inv_sup_management.repo.LocationsRepository;
import com.erpsoftware.inv_sup_management.security.ApiException;
import com.erpsoftware.inv_sup_management.services.Interfaces.InventoryServicesInterface;
import com.erpsoftware.inv_sup_management.services.Interfaces.StockServicesInterface;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.MoveInvItem;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.MoveInvItemMulti;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class InventoryServices implements InventoryServicesInterface {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private LocationsRepository locationsRepository;

    private final RedisManager Cache = new RedisManager();
    private final ObjectMapper mapper = new ObjectMapper();

    private final StockServicesInterface stockServices;

    public InventoryServices(StockServicesInterface stockServices) {
        this.stockServices = stockServices;
    }

    @Override
    public List<Inventory> getAllInventories() {
        try {
            String key = "inventory:all";
            String cacheData = Cache.getData(key);
            if (cacheData != null) {
                return mapper.readValue(cacheData, new TypeReference<List<Inventory>>() {
                });
            }
            List<Inventory> items = inventoryRepository.findAll();
            String json = mapper.writeValueAsString(items);
            Cache.setData(key, json);
            return items;
        } catch (Exception e) {
            throw new ApiException("Internal Server Error", 500);
        }
    }

    @Override
    public List<Inventory> getAllItemsByLocation(Integer id) {
        try {
            String key = "inventory:location" + id;
            String cacheData = Cache.getData(key);
            if (cacheData != null) {
                return mapper.readValue(cacheData, new TypeReference<List<Inventory>>() {
                });
            }
            List<Inventory> items = inventoryRepository.findAllByLocationId(id);
            String json = mapper.writeValueAsString(items);
            Cache.setData(key, json);
            return items;
        } catch (Exception e) {
            throw new ApiException("Internal Server Error", 500);
        }
    }

    @Override
    public List<Inventory> getAllItemsByProduct(Integer id) {
        try {
            String key = "inventory:product" + id;
            String cacheData = Cache.getData(key);
            if (cacheData != null) {
                return mapper.readValue(cacheData, new TypeReference<List<Inventory>>() {
                });
            }
            List<Inventory> items = inventoryRepository.findAllByProductId(id);
            String json = mapper.writeValueAsString(items);
            Cache.setData(key, json);
            return items;
        } catch (Exception e) {
            throw new ApiException("Internal Server Error", 500);
        }
    }

    @Override
    public Inventory getInventory(Integer id) {
        try {
            String key = "inventory:id" + id;
            String cacheData = Cache.getData(key);
            if (cacheData != null) {
                return mapper.readValue(cacheData, new TypeReference<Inventory>() {
                });
            }
            Inventory item = inventoryRepository.findById(id).orElse(null);
            String json = mapper.writeValueAsString(item);
            Cache.setData(key, json);
            return item;
        } catch (Exception e) {
            throw new ApiException("Internal Server Error", 500);
        }
    }

    @Override
    public Inventory getInventory(Integer locationId, Integer productId) {
        try {
            String key = "inventory:product" + productId + "location" + locationId;
            String cacheData = Cache.getData(key);
            if (cacheData != null) {
                return mapper.readValue(cacheData, new TypeReference<Inventory>() {
                });
            }
            Inventory item = inventoryRepository.findByLocationIdAndProductId(locationId, productId).orElse(null);
            String json = mapper.writeValueAsString(item);
            Cache.setData(key, json);
            return item;
        } catch (Exception e) {
            throw new ApiException("Internal Server Error", 500);
        }
    }

    @Override
    public Inventory saveInventory(Inventory inventory) {
        Cache.removeKeys("inventory");
        Inventory updatedItem = inventoryRepository.save(inventory);
        return updatedItem;
    }

    @Override
    @Transactional
    public Inventory moveInvItem(MoveInvItem entity) {
        Cache.removeKeys("inventory");
        Inventory movedItem = entity.item();
        Inventory originalItemBeforeMove = getInventory(movedItem.getId());
        Locations prevLocation = locationsRepository.findById(movedItem.getLocationId())
                .orElseThrow(() -> new ApiException("Unknow Location", 400));
        Locations newLocation = entity.location();
        movedItem.setLocationId(newLocation.getId());
        Inventory destinationItem = getInventory(newLocation.getId(), movedItem.getProductId());
        if (destinationItem == null) {
            destinationItem = new Inventory();
            destinationItem.setLocationId(newLocation.getId());
            destinationItem.setProductId(movedItem.getProductId());
            destinationItem.setQuantity(0);
            destinationItem.setReserved(0);
        }
        destinationItem.setQuantity(destinationItem.getQuantity() + movedItem.getQuantity());
        if (originalItemBeforeMove.getQuantity() < movedItem.getQuantity() || movedItem.getQuantity() <= 0
                || originalItemBeforeMove.getQuantity() <= 0) {
            throw new ApiException("Invalid Move Quantity Contain", 400);
        }
        originalItemBeforeMove.setQuantity(originalItemBeforeMove.getQuantity() - movedItem.getQuantity());
        stockServices.addMovement(movedItem.getProductId(), 0,
                "Move item from " + prevLocation.getCode() + " " + newLocation.getCode() + ".",
                "INV" + destinationItem.getId());
        inventoryRepository.save(originalItemBeforeMove);
        inventoryRepository.save(destinationItem);
        return movedItem;
    }

    @Override
    @Transactional
    public List<Inventory> moveInvItems(MoveInvItemMulti items) {
        Cache.removeKeys("inventory");
        List<Inventory> invItems = items.items();
        Locations newLocation = items.newLocation();
        for (Inventory invItem : invItems) {
            moveInvItem(new MoveInvItem(invItem, newLocation));
        }
        inventoryRepository.saveAll(invItems);
        return invItems;
    }

}