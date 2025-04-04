package com.bookease.bookease.repositories;
import com.bookease.bookease.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, String> {
    public Optional<Category> findByName(String name);
}
