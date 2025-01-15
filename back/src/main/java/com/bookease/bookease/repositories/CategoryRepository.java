package com.bookease.bookease.repositories;
import com.bookease.bookease.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CategoryRepository extends JpaRepository<Category, String> {
}
