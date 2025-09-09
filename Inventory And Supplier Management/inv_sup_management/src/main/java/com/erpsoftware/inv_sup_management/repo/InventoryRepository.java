package com.erpsoftware.inv_sup_management.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erpsoftware.inv_sup_management.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Integer>{
    List<Inventory> findAllByLocationId(Integer locationId);
    List<Inventory> findAllByProductId(Integer productId);
    Optional<Inventory> findByLocationIdAndProductId(Integer locationId,Integer productId);
}
