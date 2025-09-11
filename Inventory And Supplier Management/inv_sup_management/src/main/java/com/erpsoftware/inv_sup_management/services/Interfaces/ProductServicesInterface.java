package com.erpsoftware.inv_sup_management.services.Interfaces;

import java.util.List;

import com.erpsoftware.inv_sup_management.entity.Product;
import com.erpsoftware.inv_sup_management.entity.ProductCategory;

import org.springframework.stereotype.Service;

@Service
public interface ProductServicesInterface {
    List<Product> getAllProducts();
    Product getProduct(int productID);
    Product createProduct(Product data);
    Product updateProduct(Product data,int id);
    Boolean deleteProduct(int id);

    ProductCategory saveCategory(ProductCategory productCategory);
    List<ProductCategory> getAllCategories();
    ProductCategory getCategoryByID(Integer id);
    String deleteCategory(Integer id);
    
}