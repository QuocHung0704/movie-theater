package com.example.demo.entity.request;

import com.example.demo.enums.ProductType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    @NotBlank(message = "Product name is required")
    private String productName;
    private String description;
    private String imageUrl;
    @Min(value = 0, message = "Price must be non-negative")
    private Long price;
    private ProductType productType;
    private Boolean isActive;
    @Min(value = 0, message = "Stock must be non-negative")
    private Integer stock;
}
