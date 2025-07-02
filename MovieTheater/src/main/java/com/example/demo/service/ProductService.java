package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.entity.request.ProductRequest;
import com.example.demo.entity.request.ProductSearchRequest;
import com.example.demo.entity.response.ProductResponse;
import com.example.demo.enums.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.awt.*;
import java.util.List;

public interface ProductService {
    Product createProduct(ProductRequest productRequest);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    ProductResponse updateProduct(Long id, ProductRequest productRequest);

    List<Product> getProductByType(ProductType type);
    Page<ProductResponse> searchProduct(ProductSearchRequest request, Pageable pageable);

}
