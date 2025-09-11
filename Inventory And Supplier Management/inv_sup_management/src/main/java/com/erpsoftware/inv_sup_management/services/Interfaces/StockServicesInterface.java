package com.erpsoftware.inv_sup_management.services.Interfaces;

import java.util.List;

import com.erpsoftware.inv_sup_management.entity.Damage_Report;
import com.erpsoftware.inv_sup_management.entity.Stock_movements;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.AdjustStock;

import org.springframework.stereotype.Service;

@Service
public interface StockServicesInterface {
    List<Stock_movements> getStock_movements();
    List<Stock_movements> getStock_movementsByDate(String from,String to);
    Stock_movements addMovement(Integer product_id, Integer change, String reason, String source_id);
    Damage_Report addDamageReport(AdjustStock damageReport);
    Stock_movements stockAdjustment(AdjustStock adjustStock);
}
