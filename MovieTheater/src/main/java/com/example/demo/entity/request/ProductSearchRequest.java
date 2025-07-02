package com.example.demo.entity.request;

import com.example.demo.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSearchRequest {
    private String name;
    private ProductType type;
    private Boolean isActive;
}
