package com.example.demo.service.impl;

import com.example.demo.entity.Product;
import com.example.demo.entity.request.ProductRequest;
import com.example.demo.enums.ProductType;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product createProduct(ProductRequest productRequest) {
        validateProductRequest(productRequest);

        Product product = productMapper.toProduct(productRequest);
        try {
            Product savedProduct = productRepository.save(product);
            return savedProduct;
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to save product", e);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAllActive();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findByIdIsActive(id).orElse(null);
    }

    @Override
    public List<Product> getProductByType(ProductType type) {
        return productRepository.getProductByType(type);
    }

    private void validateProductRequest(ProductRequest productRequest) {
        if (productRequest.getProductName() == null || productRequest.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (productRequest.getPrice() == null || productRequest.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (productRequest.getStock() == null || productRequest.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        if (productRequest.getProductType() == null || !isValidProductType(productRequest.getProductType())) {
            throw new IllegalArgumentException("Invalid product type");
        }
    }

    private boolean isValidProductType(ProductType productType) {
        try {
            ProductType.valueOf(productType.name());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
