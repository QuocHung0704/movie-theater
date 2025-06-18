package com.example.demo.service;

import com.example.demo.entity.Type;
import com.example.demo.entity.request.TypeRequest;
import com.example.demo.entity.response.TypeResponse;

import java.util.List;

public interface TypeService {
    TypeResponse createType(TypeRequest typeRequest);
    List<Type> getTypesById(List<Long> typeIds);
}
