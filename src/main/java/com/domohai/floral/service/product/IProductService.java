package com.domohai.floral.service.product;

import com.domohai.floral.controller.product.RequestProduct;
import com.domohai.floral.model.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Integer id);
    Product addProduct(RequestProduct product);
    Product updateProduct(Integer id, RequestProduct product);
    void deleteProductById(Integer id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategoryName(String categoryName);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByCategoryNameAndName(String categoryName, String name);
}
