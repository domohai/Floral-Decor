package com.domohai.floral.controller;

import com.domohai.floral.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserDAO userDAO;
    
    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    @GetMapping("/all")
    public String getAllUsers() {
        return userDAO.findAll().toString();
    }
    
    @GetMapping("/{id}")
    public String getUserById(@PathVariable Integer id) {
        return userDAO.findById(id).toString();
    }
}
