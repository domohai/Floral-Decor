package com.domohai.floral.model;

import com.domohai.floral.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "roles", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "role"})
})
public class Role {
    @Id
    @Column(name = "id")
    private Integer id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleType role = RoleType.ROLE_USER;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    
    public Role() {
    }
    
    public RoleType getRole() {
        return role;
    }
    
    public void setRole(RoleType role) {
        this.role = role;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}
