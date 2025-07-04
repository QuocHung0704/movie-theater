package com.example.demo.service.impl;

import com.example.demo.entity.Product;
import com.example.demo.entity.request.ProductRequest;
import com.example.demo.entity.request.ProductSearchRequest;
import com.example.demo.entity.response.ProductResponse;
import com.example.demo.enums.ProductType;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ModelMapper modelMapper;

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
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findByIdIsActive(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        validateProductRequest(productRequest);
        productMapper.updateProduct(product, productRequest);
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public Product deleteProduct(Long productId) {
        Product product = productRepository.findProductByProductId(productId);
        product.setIsDeleted(true);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, Product.class);
    }

    @Override
    public List<Product> getProductByType(ProductType type) {
        return productRepository.getProductByType(type);
    }

    @Override
    public Page<ProductResponse> searchProduct(ProductSearchRequest request, Pageable pageable) {
        Page<Product> products = productRepository.findByCriteria(
                request.getName(),
                request.getType(),
                request.getIsActive(),
                pageable
        );

        return products.map(productMapper::toProductResponse);
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
