package com.domohai.floral.controller.product;

import com.domohai.floral.controller.ApiResponse;
import com.domohai.floral.exception.ProductNotFoundException;
import com.domohai.floral.model.Product;
import com.domohai.floral.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;
    
    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }
    
    // GET
    @GetMapping("")
    public ResponseEntity<ApiResponse> getProducts(
            @RequestParam(name = "categoryName", required = false) String categoryName,
            @RequestParam(name = "name", required = false) String name
    ) {
        List<Product> products;
        try {
            if (categoryName != null && name != null) {
                // Get products by category name and name
                products = productService.getProductsByCategoryNameAndName(categoryName, name);
            } else if (categoryName != null) {
                // Get products by category name
                products = productService.getProductsByCategoryName(categoryName);
            } else if (name != null) {
                // Get products by name
                products = productService.getProductsByName(name);
            } else {
                // Get all products
                products = productService.getAllProducts();
            }
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully fetched products", products));
    }
    
    // Get product by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(
            @PathVariable(name = "id") Integer id
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Successfully fetched product with id: " + id,
                    productService.getProductById(id)));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    // POST
    // Add product
    @PostMapping("")
    public ResponseEntity<ApiResponse> addProduct(
            @RequestBody RequestProduct product
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Successfully added product", productService.addProduct(product)));
    }
    
    // PUT
    // Update product
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(
            @PathVariable(name = "id") Integer id,
            @RequestBody RequestProduct product
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Successfully updated product with id: " + id,
                    productService.updateProduct(id, product)));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
}
