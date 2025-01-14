package com.bookease.bookease.services;

import com.bookease.bookease.domain.Category;
import com.bookease.bookease.dtos.category.CategoryRequestDTO;
import com.bookease.bookease.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategoriesById(List<UUID> ids){
        return categoryRepository.findAllById(ids);
    }

    public ResponseEntity createCategory(CategoryRequestDTO data){
        Category newCategory = new Category(data);
        categoryRepository.save(newCategory);
        return ResponseEntity.ok().build();

    }


    public void deleteCategory(UUID categoryId){
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category with ID " + categoryId + " does not exist");
        }
        categoryRepository.deleteById(categoryId);
    }
}
