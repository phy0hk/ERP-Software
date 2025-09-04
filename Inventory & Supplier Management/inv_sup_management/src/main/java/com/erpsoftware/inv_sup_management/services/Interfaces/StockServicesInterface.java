package com.erpsoftware.inv_sup_management.services.Interfaces;

import com.erpsoftware.inv_sup_management.entity.Stock_movements;

public interface StockServicesInterface {
    Stock_movements getStocksMovements();
    Stock_movements createStockMovements();
}
