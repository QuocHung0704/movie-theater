package com.example.demo.controller;

import com.example.demo.entity.request.TypeRequest;
import com.example.demo.entity.response.TypeResponse;
import com.example.demo.service.TypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/type")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class TypeController {
    private final TypeService typeService;

    @PostMapping("")
    public ResponseEntity<TypeResponse> createType(@RequestBody TypeRequest typeRequest) {
        TypeResponse typeResponse = typeService.createType(typeRequest);
        return new ResponseEntity<>(typeResponse, HttpStatus.CREATED);
    }
}
