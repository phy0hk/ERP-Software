package com.erpsoftware.inv_sup_management.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.entity.Product;
import com.erpsoftware.inv_sup_management.repo.ProductsRepository;
import com.erpsoftware.inv_sup_management.services.Interfaces.ProductServicesInterface;

@Service
public class ProductServices implements ProductServicesInterface{
    @Autowired
    private ProductsRepository productsRepository;

    public List<Product> getAllProducts() {
        List<Product> allProducts = productsRepository.findAll();
        return allProducts;
    }

    @Override
    public Product getProduct(int productID) {
        Product product = productsRepository.findById(productID).orElse(null);
        return product;
    }

    @Override
    public Product createProduct(Product data) {
        try {
            return productsRepository.save(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product updateProduct(Product data,int id) {
        try {
            Product existing = productsRepository.findById(id).orElse(productsRepository.findBySku(data.getSku()));
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
            if (data.getBrand() != null)
                existing.setBrand(data.getBrand());
            if (data.getCategory_id() != null)
                existing.setCategory_id(data.getCategory_id());
            existing.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            return productsRepository.save(existing);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean deleteProduct(int id){
        try {
            Product product = productsRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
            productsRepository.delete(product);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
