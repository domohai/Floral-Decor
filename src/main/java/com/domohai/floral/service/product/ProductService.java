package com.domohai.floral.service.product;

import com.domohai.floral.controller.product.RequestProduct;
import com.domohai.floral.exception.ProductNotFoundException;
import com.domohai.floral.model.Category;
import com.domohai.floral.model.Product;
import com.domohai.floral.repo.CategoryRepository;
import com.domohai.floral.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    
    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    
    // GET
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }
    
    @Override
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new ProductNotFoundException("Products not found with category name: " + categoryName));
    }
    
    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("Products not found with name: " + name));
    }
    
    @Override
    public List<Product> getProductsByCategoryNameAndName(String categoryName, String name) {
        return productRepository.findByCategoryNameAndName(categoryName, name)
                .orElseThrow(() -> new ProductNotFoundException("Products not found with category name: " + categoryName + " and name: " + name));
    }
    
    // POST
    @Override
    public Product addProduct(RequestProduct product) {
        // Check if category exists
        Optional<List<Category>> category = categoryRepository.findByName(product.getCategory());
        Category newCategory = null;
        if (category.isEmpty()) {
            // add new category
            newCategory = categoryRepository.save(new Category(product.getCategory()));
        }
        // create new product from request product
        Product newProduct = new Product(product.getName(), product.getDescription(), product.getPrice(), product.getStock(), newCategory, product.getImageUrl());
        return productRepository.save(newProduct);
    }
    
    // PUT
    @Override
    public Product updateProduct(Integer id, RequestProduct product) {
        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product to be updated not found with id: " + id));
        // Check if category exists
        Optional<List<Category>> category = categoryRepository.findByName(product.getCategory());
        Category newCategory = null;
        if (category.isEmpty()) {
            // add new category
            newCategory = categoryRepository.save(new Category(product.getCategory()));
        }
        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setStock(product.getStock());
        productToUpdate.setCategory(newCategory);
        productToUpdate.setImageUrl(product.getImageUrl());
        return productRepository.save(productToUpdate);
    }
    
    // DELETE
    @Override
    public void deleteProductById(Integer id) {
        productRepository.findById(id)
                .ifPresentOrElse(product -> productRepository.deleteById(id),
                () -> {throw new ProductNotFoundException("Product to be deleted not found with id: " + id);});
    }
}
