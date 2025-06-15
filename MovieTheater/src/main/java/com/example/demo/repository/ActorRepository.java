package com.example.demo.repository;

import com.example.demo.entity.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    Optional<Actor> findActorByActorId(Long ActorId);
    Page<Actor> findAll(Specification<Actor> spec, Pageable pageable);
}
