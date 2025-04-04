package com.bookease.bookease.integration;

import com.bookease.bookease.domain.Category;
import com.bookease.bookease.dtos.category.CategoryRequestDTO;
import com.bookease.bookease.repositories.CategoryRepository;
import com.bookease.bookease.repositories.EventRepository;
import com.bookease.bookease.repositories.OrganizerRepository;
import com.bookease.bookease.repositories.TicketRepository;
import com.bookease.bookease.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CategoryServiceIntegrationTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    public void setup(){
        ticketRepository.deleteAll();
        eventRepository.deleteAll();
        organizerRepository.deleteAll();
        categoryRepository.deleteAll();

    }

    private CategoryRequestDTO createCategoryRequest(String name) {
        return new CategoryRequestDTO("TestId", name);
    }

    @Test
    public void createCategory_ShouldSaveCategoryToDatabase(){
        CategoryRequestDTO requestDTO = createCategoryRequest("Fantasy");
        long initialCount = categoryRepository.count();

        // Act
        ResponseEntity response = categoryService.createCategory(requestDTO);

        // Assert
        assertEquals(initialCount + 1, categoryRepository.count());
        assertEquals(200, response.getStatusCode().value());

        Optional<Category> savedCategory = categoryRepository.findByName("Fantasy");
        assertTrue(savedCategory.isPresent());
        assertEquals("Fantasy", savedCategory.get().getName());
    }

    @Test
    public void createCategory_ShouldReturnOkResponse() {
        // Arrange
        CategoryRequestDTO requestDTO = createCategoryRequest("Science Fiction");

        // Act
        ResponseEntity response = categoryService.createCategory(requestDTO);

        // Assert
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void createCategory_ShouldPersistCorrectData() {
        // Arrange
        CategoryRequestDTO requestDTO = createCategoryRequest("Mystery");

        // Act
        categoryService.createCategory(requestDTO);

        // Assert
        Category savedCategory = categoryRepository.findByName("Mystery").orElseThrow();
        assertEquals("Mystery", savedCategory.getName());
    }


}
