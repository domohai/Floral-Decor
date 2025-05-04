package com.domohai.floral.old.dto;

import java.util.List;

public class JWTResponse {
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String email;
    private String name;
    private List<String> roles;
    
    public JWTResponse(String token, Integer id, String email, String name, List<String> roles) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.name = name;
        this.roles = roles;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<String> getRoles() {
        return this.roles;
    }
    
    public void setRole(List<String> roles) {
        this.roles = roles;
    }
}
