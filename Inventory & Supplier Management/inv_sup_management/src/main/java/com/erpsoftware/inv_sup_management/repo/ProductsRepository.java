package com.erpsoftware.inv_sup_management.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erpsoftware.inv_sup_management.entity.Product;

@Repository
public interface ProductsRepository extends JpaRepository<Product,Integer> {
    Product findBySku(String sku);
    Optional<Product> findOptionalBySku(String sku);
}
