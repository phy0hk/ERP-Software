package com.erpsoftware.inv_sup_management.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.erpsoftware.inv_sup_management.entity.Stock_movements;

@Repository
public interface StockMovementRepository extends JpaRepository<Stock_movements,Integer>{
    @Query("SELECT s from Stock_movements s WHERE s.created_at>=:from AND s.created_at<=:to")
    List<Stock_movements> findByDateBetween(@Param("from") java.sql.Timestamp from,@Param("from") Timestamp to);
}
