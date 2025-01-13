package com.bookease.bookease.repositories.specification;

import com.bookease.bookease.domain.Category;
import com.bookease.bookease.domain.Event;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecifications {

    public static Specification<Event> hasCategories(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            Join<Event, Category> categoryJoin = root.join("categories");
            return criteriaBuilder.equal(categoryJoin.get("name"), categoryName);
        };
    }

    public static Specification<Event> hasCapacityGreaterThanOrEqualTo(int capacity) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("capacity"), capacity);
    }
}
