package com.domohai.floral.infrastructure.persistence;

import com.domohai.floral.domain.model.Role;
import com.domohai.floral.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final JpaRoleRepository jpaRoleRepository;
    
    @Override
    public Optional<Role> findByName(String name) {
        return jpaRoleRepository.findByName(name)
                .map(this::toDomain);
    }
    
    private Role toDomain(RoleEntity entity) {
        return Role.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
