package com.domohai.floral.infrastructure.persistence;

import com.domohai.floral.domain.model.Role;
import com.domohai.floral.domain.model.User;
import com.domohai.floral.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;
    private final JpaRoleRepository jpaRoleRepository;
    
    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(this::toDomain);
    }
    
    @Override
    public User save(User user) {
        UserEntity saved = jpaUserRepository.save(toEntity(user));
        return toDomain(saved);
    }
    
    private UserEntity toEntity(User user) {
        // Important: Find existing role entities by name instead of creating new ones
        Set<RoleEntity> roleEntities = user.getRoles().stream()
                .map(role -> jpaRoleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role.getName())))
                .collect(Collectors.toSet());
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(roleEntities)
                .build();
    }
    
    private User toDomain(UserEntity entity) {
        Set<Role> roles = entity.getRoles().stream()
                .map(roleEntity -> Role.builder()
                        .id(roleEntity.getId())
                        .name(roleEntity.getName())
                        .build())
                .collect(Collectors.toSet());
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .roles(roles)
                .build();
    }
}
