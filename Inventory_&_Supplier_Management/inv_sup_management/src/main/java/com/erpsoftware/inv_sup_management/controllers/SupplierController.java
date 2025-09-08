package com.erpsoftware.inv_sup_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erpsoftware.inv_sup_management.entity.Suppliers;
import com.erpsoftware.inv_sup_management.security.AuthGuard;
import com.erpsoftware.inv_sup_management.services.Interfaces.SupplierServicesInterface;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.StatusResponder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/v1")
@AuthGuard
public class SupplierController {

    @Autowired
    private final SupplierServicesInterface supplierServices;

    public SupplierController(SupplierServicesInterface supplierServices){
        this.supplierServices = supplierServices;
    }

    @GetMapping("/suppliers")
    public StatusResponder<List<Suppliers>> getAllSupplier() {
        List<Suppliers> suppliers = this.supplierServices.getAllSupplier();
        return new StatusResponder<>("ok", suppliers);   
    }
    
    @GetMapping("/supplier")
    public StatusResponder<Suppliers> getSupplier(@RequestParam(required = false) Integer id,
    @RequestParam(required = false) String name) {
        Suppliers supplier;
        if(id!=null){
            supplier = this.supplierServices.getSupplierById(id);
        }else if(name!=null){
            supplier = this.supplierServices.getSupplierByCompanyName(name);
        }else {
        throw new RuntimeException("Must provide either id or name");
    }
        return new StatusResponder<>("ok", supplier);
    }

    @PostMapping("/supplier")
    public StatusResponder<Suppliers> createSupplier(@RequestBody Suppliers supplier) {
        Suppliers newSupplier = this.supplierServices.createSupplier(supplier);
        return new StatusResponder<>("ok",newSupplier);
    }
    
    @PutMapping("/supplier/{id}")
    public StatusResponder<Suppliers> updateSupplier(@PathVariable int id, @RequestBody Suppliers entity) {
        Suppliers updatedSupplier = this.supplierServices.updateSupplier(entity, id);
        return new StatusResponder<>("ok", updatedSupplier);
    }

    @DeleteMapping("/supplier/{id}")
    public StatusResponder<String> deleteSupplier(@PathVariable int id){
        Boolean deleted = this.supplierServices.deleteSupplier(id);
        if(deleted){
            return new StatusResponder<>("ok","Deleted Successfully!");
        }
        return new StatusResponder<>("bad", "Failed to delete supplier");
    }
     
    
}
