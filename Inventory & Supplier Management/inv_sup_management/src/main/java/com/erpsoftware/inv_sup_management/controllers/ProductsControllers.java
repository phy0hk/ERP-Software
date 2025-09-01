package com.erpsoftware.inv_sup_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erpsoftware.inv_sup_management.entity.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.erpsoftware.inv_sup_management.services.ProductServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class ProductsControllers {

    private final ProductServices productServices;

    public ProductsControllers(ProductServices productServices) {
        this.productServices = productServices;
    }

    // Get products from db
    @GetMapping("/products")
    public StatusResponder<List<Product>> getMethodName() {
        List<Product> allProducts = productServices.getAllProducts();
        if (allProducts != null)
            return new StatusResponder<List<Product>>("ok", allProducts);
        return new StatusResponder<List<Product>>("not found", null);
    }

    @GetMapping("/product/{id}")
    public StatusResponder<Product> getMethodName(@PathVariable int id) {
        Product product = productServices.getProduct(id);
        if (product != null)
            return new StatusResponder<Product>("ok", product);
        return new StatusResponder<Product>("not found", null);
    }

    @PostMapping("/products")
    public StatusResponder<Product> postMethodName(@RequestBody Product entity) {
        Product res = productServices.createProduct(entity);
        if(res!=null) return new StatusResponder<>("ok", res);
        return new StatusResponder<>("error", null);
    }

    @PutMapping("/product/{id}")
    public StatusResponder<Product> putMethodName(@RequestBody Product entity) {
        Product res = productServices.updateProduct(entity);
        if(res!=null) return new StatusResponder<>("ok", res);
        return new StatusResponder<>("error", null);
    }

    // Records or Json format
    public record StatusResponder<T>(String status, T data) {}

}
