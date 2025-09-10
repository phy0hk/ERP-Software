package com.erpsoftware.inv_sup_management.services.Interfaces;

import java.util.List;

import com.erpsoftware.inv_sup_management.entity.Stock_movements;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.DamageReport;

import org.springframework.stereotype.Service;

@Service
public interface StockServicesInterface {
    List<Stock_movements> getStock_movements();
    List<Stock_movements> getStock_movementsByDate(String from,String to);
    String addMovement(Integer product_id, Integer change, String reason, String source_id);
    DamageReport addDamageReport(DamageReport damageReport);
}
