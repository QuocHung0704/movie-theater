package com.example.demo.mapper.impl;

import com.example.demo.entity.Actor;
import com.example.demo.entity.request.ActorRequest;
import com.example.demo.entity.response.ActorResponse;
import com.example.demo.mapper.ActorMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ActorMapperImpl implements ActorMapper {
    @Override
    public Actor toActor(ActorRequest actor) {
        return Actor.builder()
                .actorName(actor.getActorName())
                .isActive(Boolean.TRUE)
                .build();
    }

    @Override
    public ActorResponse toActorResponse(Actor actor) {
        return ActorResponse.builder()
                .actorId(actor.getActorId())
                .actorName(actor.getActorName())
                .isActive(actor.getIsActive())
                .build();
    }
}
