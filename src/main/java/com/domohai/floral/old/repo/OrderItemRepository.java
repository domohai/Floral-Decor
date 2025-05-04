package com.domohai.floral.old.repo;

import com.domohai.floral.old.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
