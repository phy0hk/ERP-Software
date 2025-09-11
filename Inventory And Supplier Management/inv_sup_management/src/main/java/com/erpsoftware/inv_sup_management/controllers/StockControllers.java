package com.erpsoftware.inv_sup_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erpsoftware.inv_sup_management.entity.Damage_Report;
import com.erpsoftware.inv_sup_management.entity.Stock_movements;
import com.erpsoftware.inv_sup_management.repo.StockMovementRepository;
import com.erpsoftware.inv_sup_management.security.AuthGuard;
import com.erpsoftware.inv_sup_management.services.Interfaces.StockServicesInterface;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.AdjustStock;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.StatusResponder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@AuthGuard
@RequestMapping("/api/v1")
public class StockControllers {
    
    private final StockServicesInterface stockServices;
    public StockControllers(StockServicesInterface stockServices){
        this.stockServices = stockServices;
    }

    //Types (In/Out)
    //Out (Bought/Damage)
    @PostMapping("/stock/damage-report")
    public StatusResponder<Damage_Report> damageReport(@RequestBody AdjustStock damageReport) {
        Damage_Report newReport = stockServices.addDamageReport(damageReport);
        return new StatusResponder<>("ok",newReport);
    }
    
    @PostMapping("/stock/adjustStock")
    public StatusResponder<Stock_movements> adjustStockUpadte(@RequestBody AdjustStock adjustStock) {
        Stock_movements newAdjustment = stockServices.stockAdjustment(adjustStock);
        return new StatusResponder<>("ok",newAdjustment);
    }
    
}
