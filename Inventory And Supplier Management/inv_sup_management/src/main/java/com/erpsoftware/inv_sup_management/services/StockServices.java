package com.erpsoftware.inv_sup_management.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.entity.Stock_movements;
import com.erpsoftware.inv_sup_management.repo.StockMovementRepository;
import com.erpsoftware.inv_sup_management.services.Interfaces.StockServicesInterface;

@Service
public class StockServices implements StockServicesInterface {

    @Autowired
    private final StockMovementRepository stockMovementRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StockServices(StockMovementRepository stockMovementRepository){
        this.stockMovementRepository = stockMovementRepository;
    }    

    @Override
    public List<Stock_movements> getStock_movements() {
        List<Stock_movements> stock_movements = stockMovementRepository.findAll();
        return stock_movements;
    }

    @Override
    public List<Stock_movements> getStock_movementsByDate(String from, String to) {
        LocalDateTime fromDateTime = LocalDateTime.parse(from,formatter);
        LocalDateTime toDateTime = LocalDateTime.parse(to,formatter);

        Timestamp fromDate = Timestamp.valueOf(fromDateTime);
        Timestamp toDate = Timestamp.valueOf(toDateTime);

        List<Stock_movements> stock_movements = stockMovementRepository.findByDateBetween(fromDate, toDate);
        return stock_movements;
    }

    @Override
    public String addMovement(Integer product_id, Integer change, String reason, String source_id) {
        Stock_movements newMovement = new Stock_movements();
        newMovement.setProduct_id(product_id);
        newMovement.setChange(change);
        newMovement.setReason(reason);
        newMovement.setSource_id(source_id);
        stockMovementRepository.save(newMovement);
        return "Add movement";
    }

    
}
