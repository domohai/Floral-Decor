package com.domohai.floral.old.repo;

import com.domohai.floral.old.enums.RoleType;
import com.domohai.floral.old.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource(exported = false)
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(RoleType role);
}
