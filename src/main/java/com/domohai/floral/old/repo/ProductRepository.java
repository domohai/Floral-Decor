package com.domohai.floral.old.repo;

import com.domohai.floral.old.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource(exported = false)
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p JOIN p.category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :categoryName, '%'))")
    Optional<List<Product>> findByCategoryName(@Param("categoryName") String categoryName);
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Optional<List<Product>> findByName(@Param("name") String name);
    @Query("SELECT p FROM Product p JOIN p.category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :categoryName, '%')) AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Optional<List<Product>> findByCategoryNameAndName(String categoryName, String name);
}
