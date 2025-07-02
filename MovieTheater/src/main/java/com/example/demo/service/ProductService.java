package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.entity.request.ProductRequest;

import java.awt.*;
import java.util.List;

public interface ProductService {
    Product createProduct(ProductRequest productRequest);
    List<Product> getAllProducts();
}
