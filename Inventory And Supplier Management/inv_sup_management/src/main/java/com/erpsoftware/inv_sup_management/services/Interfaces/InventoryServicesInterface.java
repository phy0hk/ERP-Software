package com.erpsoftware.inv_sup_management.services.Interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.entity.Inventory;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.MoveInvItem;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.MoveInvItemMulti;

@Service
public interface InventoryServicesInterface {
    List<Inventory> getAllInventories();
    List<Inventory> getAllItemsByLocation(Integer id);
    List<Inventory> getAllItemsByProduct(Integer id);
    Inventory getInventory(Integer id);
    Inventory getInventory(Integer locationId,Integer productId);

    Inventory saveInventory(Inventory inventory);
    Inventory moveInvItem(MoveInvItem entity);
    List<Inventory> moveInvItems(MoveInvItemMulti items);
}
