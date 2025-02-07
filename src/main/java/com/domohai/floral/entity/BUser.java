package com.domohai.floral.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class BUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "role")
    private String role;
    
    @Column(name = "service_point_id")
    private Integer servicePointId;
    
    @Column(name = "collection_point_id")
    private Integer collectionPointId;
    
    public BUser() {
    }
    
    public BUser(String name, String email, String password, String role, Integer servicePointId, Integer collectionPointId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.servicePointId = servicePointId;
        this.collectionPointId = collectionPointId;
    }
    
    @Override
    public String toString() {
        return "BUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", servicePointId=" + servicePointId +
                ", collectionPointId=" + collectionPointId +
                '}';
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public Integer getServicePointId() {
        return servicePointId;
    }
    
    public void setServicePointId(Integer servicePointId) {
        this.servicePointId = servicePointId;
    }
    
    public Integer getCollectionPointId() {
        return collectionPointId;
    }
    
    public void setCollectionPointId(Integer collectionPointId) {
        this.collectionPointId = collectionPointId;
    }
}
