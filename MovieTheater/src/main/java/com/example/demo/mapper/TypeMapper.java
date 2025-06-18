package com.example.demo.mapper;

import com.example.demo.entity.Type;
import com.example.demo.entity.request.TypeRequest;
import com.example.demo.entity.response.TypeResponse;

public interface TypeMapper {
    Type toType(TypeRequest request);
    TypeResponse toTypeResponse(Type type);
}
