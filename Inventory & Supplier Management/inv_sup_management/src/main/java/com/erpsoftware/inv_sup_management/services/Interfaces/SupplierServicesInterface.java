package com.erpsoftware.inv_sup_management.services.Interfaces;

import java.util.List;

import com.erpsoftware.inv_sup_management.entity.Suppliers;

public interface SupplierServicesInterface {
    List<Suppliers> getAllSupplier();   
    Suppliers getSupplierById(int id);
    Suppliers getSupplierByCompanyName(String name);
    Suppliers updateSupplier(Suppliers supplier,int id);
    Suppliers createSupplier(Suppliers supplier);
    Boolean deleteSupplier(int id);
}