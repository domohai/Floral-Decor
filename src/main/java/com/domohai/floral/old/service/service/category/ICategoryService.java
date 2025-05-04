package com.domohai.floral.old.service.service.category;

import com.domohai.floral.old.controller.category.RequestCategory;
import com.domohai.floral.old.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Integer id);
    List<Category> getCategoryByName(String name);
    Category addCategory(RequestCategory category);
    Category updateCategory(Integer id, RequestCategory category);
    void deleteCategory(Integer id);
}
