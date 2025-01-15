package com.bookease.bookease.services;

import com.bookease.bookease.domain.Category;
import com.bookease.bookease.dtos.category.CategoryRequestDTO;
import com.bookease.bookease.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.lang.String;
import java.util.List;


@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategoriesById(List<String> ids){
        return categoryRepository.findAllById(ids);
    }

    public ResponseEntity createCategory(CategoryRequestDTO data){
        Category newCategory = new Category(data);
        categoryRepository.save(newCategory);
        return ResponseEntity.ok().build();

    }


    public void deleteCategory(String categoryId){
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category with ID " + categoryId + " does not exist");
        }
        categoryRepository.deleteById(categoryId);
    }
}
