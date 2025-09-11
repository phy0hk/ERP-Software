package com.erpsoftware.inv_sup_management.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.entity.Damage_Report;
import com.erpsoftware.inv_sup_management.entity.Inventory;
import com.erpsoftware.inv_sup_management.entity.Stock_movements;
import com.erpsoftware.inv_sup_management.repo.DamageReportRepository;
import com.erpsoftware.inv_sup_management.repo.InventoryRepository;
import com.erpsoftware.inv_sup_management.repo.StockMovementRepository;
import com.erpsoftware.inv_sup_management.security.ApiException;
import com.erpsoftware.inv_sup_management.services.Interfaces.StockServicesInterface;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.AdjustStock;

import jakarta.transaction.Transactional;

@Service
public class StockServices implements StockServicesInterface {

    @Autowired
    private StockMovementRepository stockMovementRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private DamageReportRepository damageReportRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
    @Transactional
    public Stock_movements addMovement(Integer product_id, Integer change, String reason, String source_id) {
        Stock_movements newMovement = new Stock_movements();
        newMovement.setProduct_id(product_id);
        newMovement.setChange(change);
        newMovement.setReason(reason);
        newMovement.setSource_id(source_id);
        stockMovementRepository.save(newMovement);
        return newMovement;
    }

    @Override
    @Transactional
    public Damage_Report addDamageReport(AdjustStock damageReport) {
        Damage_Report newReport = new Damage_Report();
        newReport.setProduct_id(damageReport.product_id());
        newReport.setQuantity(damageReport.quantity());
        newReport.setReason(damageReport.reason());
        newReport.setReported_by(damageReport.reportedBy());
        newReport.setSource_ref(damageReport.sourceRef());

        Inventory damagedItem = inventoryRepository.findByLocationIdAndProductId(damageReport.locationId(),damageReport.product_id()).orElseThrow(()->new ApiException("Inventory Item Not Found", 400));
        
        if(damagedItem==null) throw new ApiException("There is no such item", 400);
        if(damagedItem.getQuantity()<damageReport.quantity()) throw new ApiException("Damage item exceed current stocks",400);
        if(damageReport.quantity()<0)throw new ApiException("Damage quantity should not be negative", 0);
        newReport = damageReportRepository.save(newReport);
        damagedItem.setQuantity(damagedItem.getQuantity()-newReport.getQuantity());
        addMovement(damageReport.product_id(), -damageReport.quantity(), damageReport.reason(), "INV"+damagedItem.getId());
        return newReport;
    }

    @Override
    @Transactional
    public Stock_movements stockAdjustment(AdjustStock adjustStock){
        Inventory adjustInventory = inventoryRepository.findByLocationIdAndProductId(adjustStock.locationId(),adjustStock.product_id()).orElseThrow(()->new ApiException("Inventory Item Not Found", 400));
        if(adjustStock.quantity()<=0)throw new ApiException("The quantity didn't have to be negative", 0);
        switch (adjustStock.adjustType()) {
            //add stock
            case 1:
                adjustInventory.setQuantity(adjustInventory.getQuantity()+adjustStock.quantity());
                Stock_movements stock_movements_add = addMovement(adjustStock.product_id(), adjustStock.quantity(), adjustStock.reason(), "INV"+adjustInventory.getId());
                return stock_movements_add;
            //remove from stock
            case 2:
                if(adjustInventory.getQuantity()<adjustStock.quantity()) throw new ApiException("Remove item exceed existing stock",400);
                adjustInventory.setQuantity(adjustInventory.getQuantity()+adjustStock.quantity());
                Stock_movements stock_movements_remove = addMovement(adjustStock.product_id(), -adjustStock.quantity(), adjustStock.reason(), "INV"+adjustInventory.getId());
                return stock_movements_remove;
            default:
                throw new ApiException("Unkonwn adjustment option", 500);
        }
    }


    
}
