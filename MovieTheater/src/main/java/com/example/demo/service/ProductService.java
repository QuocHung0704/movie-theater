package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.entity.request.ProductRequest;
import com.example.demo.enums.ProductType;

import java.awt.*;
import java.util.List;

public interface ProductService {
    Product createProduct(ProductRequest productRequest);
    List<Product> getAllProducts();
    Product getProductById(Long id);


    List<Product> getProductByType(ProductType type);
}
