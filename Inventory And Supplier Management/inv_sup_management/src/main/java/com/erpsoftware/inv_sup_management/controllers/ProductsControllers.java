package com.erpsoftware.inv_sup_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erpsoftware.inv_sup_management.entity.Product;
import com.erpsoftware.inv_sup_management.entity.ProductCategory;
import com.erpsoftware.inv_sup_management.security.ApiException;
import com.erpsoftware.inv_sup_management.security.AuthGuard;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.erpsoftware.inv_sup_management.services.Interfaces.ProductServicesInterface;
import com.erpsoftware.inv_sup_management.services.Interfaces.StockServicesInterface;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.StatusResponder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AuthGuard
@RequestMapping("/api/v1")
public class ProductsControllers {
    
    private final ProductServicesInterface productServices;
    private final StockServicesInterface stockservice;

    public ProductsControllers(ProductServicesInterface productServices,StockServicesInterface stockService) {
        this.productServices = productServices;
        this.stockservice = stockService;
    }
    
    // Get products from db
    @GetMapping("/products")
    public StatusResponder<List<Product>> GetAllProducts() {
        List<Product> allProducts = productServices.getAllProducts();
        if (allProducts != null)
            return new StatusResponder<List<Product>>("ok", allProducts);
        return new StatusResponder<List<Product>>("not found", null);
    }

    @GetMapping("/product/{id}")
    public StatusResponder<Product> GetProductByID(@PathVariable int id) {
        Product product = productServices.getProduct(id);
        if (product != null)
            return new StatusResponder<Product>("ok", product);
        return new StatusResponder<Product>("not found", null);
    }

    @PostMapping("/products")
    @Transactional
    public StatusResponder<Product> AddNewProduct(@RequestBody Product entity) {
        Product res = productServices.createProduct(entity);
        if(res!=null) {return new StatusResponder<>("ok", res);};
        return new StatusResponder<>("bad", null);
    }

    @PutMapping("/product/{id}")
    @Transactional
    public StatusResponder<Product> UpdateProduct(@RequestBody Product entity,@PathVariable int id) {
        Product res = productServices.updateProduct(entity,id);
        if(res!=null) return new StatusResponder<>("ok", res);
        return new StatusResponder<>("bad", null);
    }

    @DeleteMapping("/product/{id}")
    @Transactional
    public StatusResponder<String> DeleteProduct(@PathVariable int id) {
        Boolean res = productServices.deleteProduct(id);
        if(res) return new StatusResponder<>("ok", "Delete Successfully");
        return new StatusResponder<>("bad", null);
    }

    //---------Category API

    @PostMapping("/category")
    public StatusResponder<ProductCategory> createCategory(@RequestBody ProductCategory entity) {
        ProductCategory checkExistance = productServices.getCategoryByID(entity.getId());
        if(checkExistance==null){
            ProductCategory newCategory = productServices.saveCategory(entity);
            return new StatusResponder<>("ok",newCategory);
        }else{
            throw new ApiException("Please use put method for updating data", 0);
        }
    }
    
    @GetMapping("/category")
    public StatusResponder<List<ProductCategory>> getAllCategory() {
        List<ProductCategory> allCategories = productServices.getAllCategories();
        return new StatusResponder<>("ok",allCategories);
    }

    @GetMapping("/category/{id}")
    public StatusResponder<ProductCategory> getCategoryById(@PathVariable Integer id) {
        ProductCategory Category = productServices.getCategoryByID(id);
        return new StatusResponder<>("ok",Category);
    }

    @PutMapping("/category")
    public StatusResponder<ProductCategory> updateCategory(@RequestBody ProductCategory entity){
        if(entity.getId()==null)
        {
            throw new ApiException("Please provide id for update category", 400);
        }
        ProductCategory updatedCategory = productServices.saveCategory(entity);
        return new StatusResponder<>("ok",updatedCategory);
    }



}