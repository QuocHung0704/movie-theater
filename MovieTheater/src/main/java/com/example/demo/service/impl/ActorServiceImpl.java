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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;

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
    public Page<ActorResponse> findAllActorsByCriteria(Map<String, Object> criteria, Pageable pageable) {
        Specification<Actor> actorSpecification = ActorSpecification.filterBYCriteria(criteria);
        return actorRepository.findAll(actorSpecification, pageable)
                .map(actorMapper::toActorResponse);
    }


}
