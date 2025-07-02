package com.example.demo.mapper.impl;

import com.example.demo.entity.Product;
import com.example.demo.entity.request.ProductRequest;
import com.example.demo.entity.response.ProductResponse;
import com.example.demo.enums.ProductType;
import com.example.demo.mapper.ProductMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .imageUrl(productRequest.getImageUrl())
                .price(productRequest.getPrice())
                .productType(ProductType.valueOf(String.valueOf(productRequest.getProductType())))
                .isActive(productRequest.getIsActive())
                .stock(productRequest.getStock())
                .build();
    }

    @Override
    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .price(product.getPrice())
                .productType(product.getProductType().name())
                .isActive(product.getIsActive())
                .stock(product.getStock())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    @Override
    public Product updateProduct(Product product, ProductRequest productRequest) {
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setImageUrl(productRequest.getImageUrl());
        product.setPrice(productRequest.getPrice());
        product.setProductType(productRequest.getProductType());
        product.setIsActive(productRequest.getIsActive());
        product.setStock(productRequest.getStock());
        return product;
    }

}
