package com.domohai.floral.service.category;

import com.domohai.floral.controller.category.RequestCategory;
import com.domohai.floral.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Integer id);
    List<Category> getCategoryByName(String name);
    Category addCategory(RequestCategory category);
    Category updateCategory(Integer id, RequestCategory category);
    void deleteCategory(Integer id);
}
