package com.example.demo.controller;

import com.example.demo.entity.Actor;
import com.example.demo.entity.request.ActorRequest;
import com.example.demo.entity.response.ActorResponse;
import com.example.demo.service.ActorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/actor")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class ActorController {
    private final ActorService actorService;

    @PostMapping("")
    public ResponseEntity<ActorResponse> createActor(@Valid @RequestBody ActorRequest actorRequest) {
        ActorResponse actorResponse = actorService.createActor(actorRequest);
        return new ResponseEntity<>(actorResponse, HttpStatus.CREATED);
    }

    @GetMapping("{actorId}")
    public ResponseEntity<ActorResponse> getActorById(@PathVariable Long actorId) {
        ActorResponse actorResponse = actorService.getActorById(actorId);
        return new ResponseEntity<>(actorResponse, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ActorResponse>> getActorsList(
            @RequestParam Map<String, Object> criteria
    ){
        List<ActorResponse> actorResponses = actorService
                .findAllActorsByCriteria(criteria, Pageable.unpaged())
                .getContent();
        return new ResponseEntity<>(actorResponses, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<ActorResponse> updateActor(@Valid @RequestBody ActorRequest actorRequest) {
        ActorResponse actorResponse = actorService.updateActor(actorRequest);
        return new ResponseEntity<>(actorResponse, HttpStatus.OK);
    }

    @DeleteMapping("{actorId}")
    public ResponseEntity<String> deleteActor(@PathVariable Long actorId) {
        boolean deleted = actorService.deleteActor(actorId);
        if (deleted) {
            return ResponseEntity.ok("Xóa diễn viên thành công");
        } else {
            return ResponseEntity.ok("Không tìm thấy diễn viên");
        }
    }
}
