package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.entity.request.ProductRequest;
import com.example.demo.entity.request.ProductSearchRequest;
import com.example.demo.entity.response.ProductResponse;
import com.example.demo.enums.ProductType;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("api/products")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {
        try {
            Product createProduct = productService.createProduct(productRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) return ResponseEntity.notFound().build();
        return  ResponseEntity.ok(product);
    }

    @GetMapping("type/{type}")
    public ResponseEntity<List<Product>> getProductByType(@PathVariable ProductType type) {
        return ResponseEntity.ok(productService.getProductByType(type));
    }

    @GetMapping("/paging")
    public ResponseEntity<Page<ProductResponse>> searchProducts(
            @ModelAttribute ProductSearchRequest searchRequest,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ProductResponse> results = productService.searchProduct(searchRequest, pageable);
        return ResponseEntity.ok(results);
    }

    @PutMapping("{id}")
    public ResponseEntity updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        try {
            ProductResponse updateProduct = productService.updateProduct(id, productRequest);
            return ResponseEntity.ok(updateProduct);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
            Product deleteProduct = productService.deleteProduct(id);
            return ResponseEntity.ok(deleteProduct);
    }
}
