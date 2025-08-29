package com.erpsoftware.inv_sup_management.apis;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erpsoftware.inv_sup_management.entity.products;
import com.erpsoftware.inv_sup_management.repo.ProductsRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1")
public class ProductsManagement {
    @Autowired
    private ProductsRepository productsRepository;


    @GetMapping("/products")
    public List<products> getMethodName() {
        List<products> allProducts = productsRepository.findAll();
        return allProducts;
    }
    
}
