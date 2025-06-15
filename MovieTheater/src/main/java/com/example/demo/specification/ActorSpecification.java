package com.example.demo.specification;

import com.example.demo.entity.Actor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActorSpecification {
    public static Specification<Actor> filterBYCriteria(Map<String, Object> criteria) {
        return (((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            //Default auto filter active actors
            predicates.add(criteriaBuilder.equal(root.get("isActive"), Boolean.TRUE));

            String actorName = (String) criteria.get("actorName");
            if (actorName != null) {
                predicates.add(criteriaBuilder.like(root.get("actorName"), "%" + actorName + "%"));
            }

            Predicate[] predicatesArray = new Predicate[predicates.size()];
            predicates.toArray(predicatesArray);
            return criteriaBuilder.and(predicatesArray);
        }));
    }
}
