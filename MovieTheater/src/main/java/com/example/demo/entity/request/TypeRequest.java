package com.example.demo.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeRequest {
    private Long typeId;
    private String typeName;
    private Boolean isActive;
}
