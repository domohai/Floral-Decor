package com.domohai.floral.service.category;

import com.domohai.floral.controller.category.RequestCategory;
import com.domohai.floral.exception.CategoryNotFoundException;
import com.domohai.floral.model.Category;
import com.domohai.floral.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    @Override
    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
    }
    
    @Override
    public List<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with name: " + name));
    }
    
    @Override
    public Category addCategory(RequestCategory category) {
        Category newCategory = new Category(category.getName());
        return categoryRepository.save(newCategory);
    }
    
    @Override
    public Category updateCategory(Integer id, RequestCategory category) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category to be updated not found with id: " + id));
        categoryToUpdate.setName(category.getName());
        return categoryRepository.save(categoryToUpdate);
    }
    
    @Override
    public void deleteCategory(Integer id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category to be deleted not found with id: " + id));
    }
}
