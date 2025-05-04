package com.domohai.floral.old.repo;

import com.domohai.floral.old.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource(exported = false)
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE LOWER(c.name) = LOWER(CONCAT('%', :name, '%'))")
    Optional<List<Category>> findByName(@Param("name") String name);
}
