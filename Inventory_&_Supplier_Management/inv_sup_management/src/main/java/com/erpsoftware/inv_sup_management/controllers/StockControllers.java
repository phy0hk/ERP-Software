package com.erpsoftware.inv_sup_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erpsoftware.inv_sup_management.repo.StockMovementRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1")
public class StockControllers {
    
    private final StockMovementRepository stockMovementRepository;
    public StockControllers(StockMovementRepository stockMovementRepository){
        this.stockMovementRepository = stockMovementRepository;
    }

    @GetMapping("/stock-movements")
    public String getStockHistory(@RequestBody String param) {
        return new String();
    }


    //Types (In/Out)
    //Out (Bought/Damage)
    @PostMapping("/damage-report")
    public String moveStock(@RequestBody String entity) {
        return entity;
    }
    
}
