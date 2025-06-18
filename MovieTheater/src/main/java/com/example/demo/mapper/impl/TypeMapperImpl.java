package com.example.demo.mapper.impl;

import com.example.demo.entity.Type;
import com.example.demo.entity.request.TypeRequest;
import com.example.demo.entity.response.TypeResponse;
import com.example.demo.mapper.TypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeMapperImpl implements TypeMapper {

    @Override
    public Type toType(TypeRequest request) {
        return Type.builder()
                .typeName(request.getTypeName())
                .isActive(Boolean.TRUE)
                .build();
    }

    @Override
    public TypeResponse toTypeResponse(Type typeId) {
        return TypeResponse.builder()
                .typeId(typeId.getTypeId())
                .typeName(typeId.getTypeName())
                .isActive(typeId.getIsActive())
                .build();

    }


}
