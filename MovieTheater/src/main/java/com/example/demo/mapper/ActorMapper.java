package com.example.demo.mapper;

import com.example.demo.entity.Actor;
import com.example.demo.entity.request.ActorRequest;
import com.example.demo.entity.response.ActorResponse;

public interface ActorMapper {
    Actor toActor(ActorRequest actor);
    ActorResponse toActorResponse(Actor actor);
    Actor updateActor(Actor actor, ActorRequest actorRequest);
}
