package com.example.demo.mapper;

import com.example.demo.entity.Product;
import com.example.demo.entity.request.ProductRequest;
import com.example.demo.entity.response.ProductResponse;

public interface ProductMapper {
    Product toProduct(ProductRequest product);
    ProductResponse toProductResponse(Product product);
}
