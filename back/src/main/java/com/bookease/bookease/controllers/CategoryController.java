package com.bookease.bookease.controllers;

import com.bookease.bookease.domain.Category;
import com.bookease.bookease.dtos.category.CategoryRequestDTO;
import com.bookease.bookease.repositories.CategoryRepository;
import com.bookease.bookease.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @GetMapping()
    public List<Category> getAllCategories() {
        return  this.categoryRepository.findAll();
    }

    @PostMapping
    public ResponseEntity registerNewCategory(@RequestBody @Validated CategoryRequestDTO categoryData){
        return categoryService.createCategory(categoryData);
    }

}
