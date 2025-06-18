package com.example.demo.entity.response;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeResponse {
    private Long typeId;
    private String typeName;
    private Boolean isActive;
}

