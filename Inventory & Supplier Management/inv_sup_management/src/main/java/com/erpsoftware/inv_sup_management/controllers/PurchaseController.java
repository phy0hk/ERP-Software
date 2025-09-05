package com.erpsoftware.inv_sup_management.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.erpsoftware.inv_sup_management.services.Interfaces.StockServicesInterface;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class PurchaseController {

    private final StockServicesInterface stockService;

    public PurchaseController(StockServicesInterface stockService){
        this.stockService = stockService;
    }

    @GetMapping("/purchase/{productID}")
    public String getMethodName(@PathVariable Integer productID) {
        return new String();
    }
    
    
}
