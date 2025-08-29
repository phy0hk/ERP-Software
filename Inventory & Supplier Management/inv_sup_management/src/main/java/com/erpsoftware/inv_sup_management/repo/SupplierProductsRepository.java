package com.erpsoftware.inv_sup_management.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erpsoftware.inv_sup_management.entity.supplier_products;

@Repository
public interface SupplierProductsRepository extends JpaRepository<supplier_products,Integer>{}
