package com.erpsoftware.inv_sup_management.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.entity.Product;
import com.erpsoftware.inv_sup_management.repo.ProductsRepository;

@Service
public class ProductServices {
    @Autowired
    private ProductsRepository productsRepository;

    public List<Product> getAllProducts() {
        List<Product> allProducts = productsRepository.findAll();
        return allProducts;
    }

    public Product getProduct(int productID) {
        Product product = productsRepository.findById(productID).orElse(null);
        return product;
    }

    public Product createProduct(Product data) {
        try {
            return productsRepository.save(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Product updateProduct(Product data) {
        try {
            Product existing = productsRepository.findBySku(data.getSku());
            if (existing == null)
                return null;
            if (data.getName() != null)
                existing.setName(data.getName());
            if (data.getSku() != null)
                existing.setSku(data.getSku());
            if (data.getCost_price() != null)
                existing.setCost_price(data.getCost_price());
            if (data.getSelling_price() != null)
                existing.setSelling_price(data.getSelling_price());
            if (data.getQuantity() != null)
                existing.setQuantity(data.getQuantity());
            if (data.getBrand() != null)
                existing.setBrand(data.getBrand());
            if (data.getCategory() != null)
                existing.setCategory(data.getCategory());
            existing.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            return productsRepository.save(existing);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
