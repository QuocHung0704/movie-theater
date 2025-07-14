package com.example.demo.controller;

import com.example.demo.entity.request.MemberFilterRequest;
import com.example.demo.entity.request.MemberRequest;
import com.example.demo.entity.response.MemberResponse;
import com.example.demo.service.MemberManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

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

    //should use request param
    @GetMapping("")
    public ResponseEntity<?> getMembers(@ModelAttribute MemberFilterRequest memberFilterRequest) {
        Page<MemberResponse> result = memberManagementService.getMembersPage(memberFilterRequest);
        return ResponseEntity.ok(result);
    }

    @GetMapping("{memberId}")
    public ResponseEntity<MemberResponse> getMemberById(@PathVariable("memberId") Long memberId) {
        MemberResponse member = memberManagementService.getMemberById(memberId);
        return ResponseEntity.ok(member);
    }

    @PutMapping("{memberId}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable("memberId") Long memberId, @Valid MemberRequest memberRequest) {
        MemberResponse updatedMember = memberManagementService.updateMember(memberId, memberRequest);
        return ResponseEntity.ok(updatedMember);
    }

    @PutMapping("{memberId}/deactivate")
    public ResponseEntity<String> deactivateMember(@PathVariable("memberId") Long memberId) {
        String result = memberManagementService.deactiveMember(memberId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("{memberId}/activate")
    public ResponseEntity<String> activateMember(@PathVariable("memberId") Long memberId) {
        String result = memberManagementService.activeMember(memberId);
        return ResponseEntity.ok(result);
    }
}
