package com.bookease.bookease.repositories.specification;

import com.bookease.bookease.domain.Event;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecifications {

    public static Specification<Event> hasCategory(String category) {
        return (root, query, criteriaBuilder) ->
                category != null ? criteriaBuilder.equal(root.get("category"), category) : null;
    }

    public static Specification<Event> hasCapacityGreaterThanOrEqualTo(int capacity) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("capacity"), capacity);
    }
}
