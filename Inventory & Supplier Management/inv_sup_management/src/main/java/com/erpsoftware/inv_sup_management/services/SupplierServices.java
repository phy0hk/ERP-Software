package com.erpsoftware.inv_sup_management.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.entity.Suppliers;
import com.erpsoftware.inv_sup_management.repo.SupplierRepository;
import com.erpsoftware.inv_sup_management.services.Interfaces.SupplierServicesInterface;

@Service
public class SupplierServices implements SupplierServicesInterface {
    @Autowired
    private final SupplierRepository supplierRepository;
    
    public SupplierServices(SupplierRepository supplierRepository){
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Suppliers> getAllSupplier() {
        List<Suppliers> suppliers = supplierRepository.findAll();
        return suppliers;
    }

    @Override
    public Suppliers getSupplierById(int id) {
        Suppliers  supplier = supplierRepository.findById(id).orElseThrow(()->new RuntimeException("Supplier not found with id "+id));
        return supplier;
    }

    @Override
    public Suppliers getSupplierByCompanyName(String name) {
        Suppliers supplier = supplierRepository.findByName(name);
        return supplier;
    }

    @Override
    public Suppliers createSupplier(Suppliers supplier) {
        Suppliers addedSupplier = supplierRepository.save(supplier);
        return addedSupplier;
    }

   @Override
public Suppliers updateSupplier(Suppliers supplier, int id) {
    Suppliers existing = supplierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Supplier not found with id " + id));

    if (supplier.getName() != null) {
        existing.setName(supplier.getName());
    }
    if (supplier.getContact_name() != null) {
        existing.setContact_name(supplier.getContact_name());
    }
    if (supplier.getPhone() != null) {
        existing.setPhone(supplier.getPhone());
    }
    if (supplier.getEmail() != null) {
        existing.setEmail(supplier.getEmail());
    }
    if (supplier.getAddress() != null) {
        existing.setAddress(supplier.getAddress());
    }
    if (supplier.getNotes() != null) {
        existing.setNotes(supplier.getNotes());
    }

    return supplierRepository.save(existing);
}
    @Override
    public Boolean deleteSupplier(int id){
        try {
            supplierRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
