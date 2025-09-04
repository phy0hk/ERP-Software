package com.erpsoftware.inv_sup_management.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erpsoftware.inv_sup_management.entity.Suppliers;

@Repository
public interface SupplierRepository extends JpaRepository<Suppliers,Integer> {
    Suppliers findByName(String name);
    Optional<Suppliers> findOptionalByName(String name);
}
