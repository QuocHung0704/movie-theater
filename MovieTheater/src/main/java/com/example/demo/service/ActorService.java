package com.example.demo.service;

import com.example.demo.entity.Actor;
import com.example.demo.entity.request.ActorRequest;
import com.example.demo.entity.response.ActorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ActorService {
    ActorResponse createActor(ActorRequest actorRequest);

    ActorResponse getActorById(Long actorId);

    ActorResponse updateActor(ActorRequest actorRequest);

    boolean deleteActor(Long actorId);

    List<ActorResponse> importDataFromExcel(MultipartFile file) throws IOException;

    Page<ActorResponse> findAllActorsByCriteria(Map<String, Object> criteria, Pageable pageable);

}
