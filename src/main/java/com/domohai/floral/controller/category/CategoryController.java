package com.domohai.floral.controller.category;

import com.domohai.floral.controller.ApiResponse;
import com.domohai.floral.exception.CategoryNotFoundException;
import com.domohai.floral.model.Category;
import com.domohai.floral.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final ICategoryService categoryService;
    
    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    // GET
    @GetMapping("")
    public ResponseEntity<ApiResponse> getCategories(
        @RequestParam(name = "name", required = false) String name
    ) {
        List<Category> categories;
        try {
            if (name != null) {
                // Get category by name
                categories = categoryService.getCategoryByName(name);
            } else {
                // Get all categories
                categories = categoryService.getAllCategories();
            }
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Categories retrieved successfully", categories));
    }
    
    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(
        @PathVariable(name = "id") Integer id
    ) {
        Category category;
        try {
            category = categoryService.getCategoryById(id);
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Category retrieved successfully", category));
    }
    
    // POST
    @PostMapping("")
    public ResponseEntity<ApiResponse> addCategory(
            @RequestBody RequestCategory category
    ) {
        Category newCategory = categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Category added successfully", newCategory));
    }
    
    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(
        @PathVariable(name = "id") Integer id,
        @RequestBody RequestCategory category
    ) {
        Category updatedCategory;
        try {
            updatedCategory = categoryService.updateCategory(id, category);
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Category updated successfully", updatedCategory));
    }
    
    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable(name = "id") Integer id
    ) {
        try {
            categoryService.deleteCategory(id);
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("Category deleted successfully", null));
    }
}
