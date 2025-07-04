package com.example.demo.controller;

import com.example.demo.entity.request.MemberRequest;
import com.example.demo.entity.response.MemberResponse;
import com.example.demo.service.MemberManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/members")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MemberManagementController {
    @Autowired
    private MemberManagementService memberManagementService;

    @PostMapping("")
    public ResponseEntity<MemberResponse> createMember(@Valid MemberRequest memberRequest) {
        MemberResponse createMember = memberManagementService.createMember(memberRequest);
        return ResponseEntity.ok(createMember);
    }
}
