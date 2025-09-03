package com.erpsoftware.inv_sup_management.services.Interfaces;

import java.util.List;

import com.erpsoftware.inv_sup_management.entity.Product;

public interface ProductServicesInterface {
    List<Product> getAllProducts();
    Product getProduct(int productID);
    Product createProduct(Product data);
    Product updateProduct(Product data);
}