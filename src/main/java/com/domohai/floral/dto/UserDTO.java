package com.domohai.floral.dto;

import com.domohai.floral.model.User;

public class UserDTO {
    private String name;
    private String email;
    
    public UserDTO() {
    }
    
    public UserDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    public UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
