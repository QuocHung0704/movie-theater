package com.example.demo.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long productId;
    private String productName;
    private String description;
    private String imageUrl;
    private Long price;
    private String productType;
    private Boolean isActive;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
