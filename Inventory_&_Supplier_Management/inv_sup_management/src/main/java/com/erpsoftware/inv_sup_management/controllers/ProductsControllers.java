package com.erpsoftware.inv_sup_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erpsoftware.inv_sup_management.entity.Product;
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

@RestController
@RequestMapping("/api/v1")
@AuthGuard
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
        stockservice.addMovement(res.getId(),res.getQuantity(),"New Product",res.getId());
        if(res!=null) {return new StatusResponder<>("ok", res);};
        return new StatusResponder<>("error", null);
    }

    @PutMapping("/product/{id}")
    @Transactional
    public StatusResponder<Product> UpdateProduct(@RequestBody Product entity,@PathVariable int id) {
        Integer newQty = entity.getQuantity();
        Product beforeChg = productServices.getProduct(id);
        Integer originalQty = beforeChg.getQuantity();
        Integer changedQty = newQty - originalQty;
        if(changedQty!=0){
            stockservice.addMovement(id, changedQty, "Update Product", id);
        }
        Product res = productServices.updateProduct(entity,id);
        if(res!=null) return new StatusResponder<>("ok", res);
        return new StatusResponder<>("error", null);
    }
    @DeleteMapping("/product/{id}")
    @Transactional
    public StatusResponder<String> DeleteProduct(@PathVariable int id) {
        Product product = productServices.getProduct(id);
        stockservice.addMovement(product.getId(), -product.getQuantity(), "Product Removal", product.getId());
        Boolean res = productServices.deleteProduct(id);
        if(res) return new StatusResponder<>("ok", "Delete Successfully");
        return new StatusResponder<>("error", null);
    }

    // Records or Json format


}


//org 5 chg 6  1
//org 6 chg 5 -1