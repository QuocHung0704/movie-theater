package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.entity.request.ProductRequest;

public interface ProductService {
    Product createProduct(ProductRequest productRequest);

}
