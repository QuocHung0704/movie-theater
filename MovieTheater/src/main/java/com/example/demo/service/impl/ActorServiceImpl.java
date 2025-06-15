package com.example.demo.service.impl;

import com.example.demo.entity.Actor;
import com.example.demo.entity.request.ActorRequest;
import com.example.demo.entity.response.ActorResponse;
import com.example.demo.mapper.ActorMapper;
import com.example.demo.repository.ActorRepository;
import com.example.demo.service.ActorService;
import com.example.demo.specification.ActorSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final ActorMapper actorMapper;
    private final ActorRepository actorRepository;

    @Override
    public ActorResponse createActor(ActorRequest actorRequest) {
        Actor actor = actorMapper.toActor(actorRequest);
        Actor savedActor = actorRepository.save(actor);
        return actorMapper.toActorResponse(savedActor);
    }

    private Actor findActorByActorId(Long actorId) {
        return actorRepository.findById(actorId).
                orElseThrow(() -> new EntityNotFoundException("Actor not exists with id: " + actorId));
    }

    @Override
    public ActorResponse getActorById(Long actorId) {
        Actor actor = findActorByActorId(actorId);
        return actorMapper.toActorResponse(actor);
    }

    @Override
    public ActorResponse updateActor(ActorRequest actorRequest) {
        Actor actor = findActorByActorId(actorRequest.getActorId());
        actor = actorMapper.updateActor(actor, actorRequest);
        Actor savedActor = actorRepository.save(actor);
        return actorMapper.toActorResponse(savedActor);
    }

    @Override
    public boolean deleteActor(Long actorId) {
        Actor actor = findActorByActorId(actorId);
        if (actor == null) {
            return false;
        }

        actor.setIsActive(Boolean.FALSE);
        actorRepository.save(actor);
        return true;
    }

    @Override
    public List<ActorResponse> importDataFromExcel(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        List<Actor> actors = new ArrayList<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row

            ActorRequest actorRequest = ActorRequest.builder()
                    .actorName(row.getCell(0).getStringCellValue())
                    .build();

            Actor actor = actorMapper.toActor(actorRequest);
            actors.add(actor);
        }

        List<Actor> savedActors = actorRepository.saveAll(actors); // Save and get entities with ID

        //Map actor entites với ActorResponse
        List<ActorResponse> responses = savedActors.stream()
                .map(actorMapper::toActorResponse)
                .collect(Collectors.toList());

        return responses;
    }


    @Override
    public Page<ActorResponse> findAllActorsByCriteria(Map<String, Object> criteria, Pageable pageable) {
        Specification<Actor> actorSpecification = ActorSpecification.filterBYCriteria(criteria);
        return actorRepository.findAll(actorSpecification, pageable)
                .map(actorMapper::toActorResponse);
    }


}
