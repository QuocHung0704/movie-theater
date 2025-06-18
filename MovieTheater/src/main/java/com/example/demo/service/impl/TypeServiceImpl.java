package com.example.demo.service.impl;

import com.example.demo.entity.Type;
import com.example.demo.entity.request.TypeRequest;
import com.example.demo.entity.response.TypeResponse;
import com.example.demo.mapper.TypeMapper;
import com.example.demo.repository.TypeRepository;
import com.example.demo.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;

    @Override
    public TypeResponse createType(TypeRequest typeRequest) {
        Type type = typeMapper.toType(typeRequest);
        Type savedType = typeRepository.save(type);
        return typeMapper.toTypeResponse(savedType);
    }

    @Override
    public List<Type> getTypesById(List<Long> typeIds) {
        return typeRepository.findTypesByTypeIdIsIn(typeIds);
    }
}
