package com.erpsoftware.inv_sup_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erpsoftware.inv_sup_management.repo.StockMovementRepository;
import com.erpsoftware.inv_sup_management.security.AuthGuard;
import com.erpsoftware.inv_sup_management.services.Interfaces.StockServicesInterface;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.DamageReport;
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

    @GetMapping("/stock-movements")
    public String getStockHistory(@RequestBody Integer param) {
        return new String();
    }


    //Types (In/Out)
    //Out (Bought/Damage)
    @PostMapping("/damage-report")
    public StatusResponder<DamageReport> damageReport(@RequestBody DamageReport damageReport) {
        DamageReport newReport = stockServices.addDamageReport(damageReport);
        return new StatusResponder<>("ok",newReport);
    }
    
}
