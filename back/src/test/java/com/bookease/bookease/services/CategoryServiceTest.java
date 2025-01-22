package com.bookease.bookease.services;
import com.bookease.bookease.domain.Category;
import com.bookease.bookease.dtos.category.CategoryRequestDTO;
import com.bookease.bookease.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category mockCategory;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        //gonna mock some categories
        Category mockCategory = new Category();

        mockCategory.setId("1");
        mockCategory.setName("MockCategory");
        mockCategory.setDescription("Category used for testing");

        this.mockCategory = mockCategory;

    }

    @Test
    @DisplayName("Should get all categories given a list of ids")
    void getAllCategoriesById() {

        Category secondCateogry = new Category();
        secondCateogry.setId("2");
        secondCateogry.setName("secondCateg");
        secondCateogry.setDescription("Category second");


        List<Category> categories = List.of(mockCategory, secondCateogry);

        List<String> categoryIds = List.of("1","2");

        Mockito.when(categoryRepository.findAllById(categoryIds)).thenReturn(categories);

        List<Category> result = categoryService.getAllCategoriesById(categoryIds);

        assertEquals(result, categories);
    }

    @Test
    @DisplayName("Should sucessfully create a category")
    void createCategory() {
        CategoryRequestDTO data = new CategoryRequestDTO("Dto desc", "categoryName");
        Category category = new Category(data);

        Mockito.when(categoryRepository.save(Mockito.any(Category.class)))
                .thenReturn(category);

        ResponseEntity response = categoryService.createCategory(data);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    @DisplayName("Should successfully delete a category by id")
    void deleteCategory() {
        Mockito.when(categoryRepository.existsById(mockCategory.getId())).thenReturn(true);
        categoryService.deleteCategory(mockCategory.getId());


        Mockito.verify(categoryRepository, Mockito.times(1)).existsById(mockCategory.getId());
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(mockCategory.getId());

    }

    @Test
    @DisplayName("Should throw an exception when category is not found")
    void deleteCategoryCase2() {
        Mockito.when(categoryRepository.existsById(mockCategory.getId())).thenReturn(false);


        assertThatThrownBy(() -> categoryService.deleteCategory(mockCategory.getId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Category with ID " + mockCategory.getId() + " does not exist");

        Mockito.verify(categoryRepository, Mockito.times(1)).existsById(mockCategory.getId());

        Mockito.verify(categoryRepository, never()).deleteById(mockCategory.getId());

    }
}