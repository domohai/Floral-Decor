package com.domohai.floral.service.user;

import com.domohai.floral.controller.user.RegisterRequest;
import com.domohai.floral.model.User;

public interface IUserService {
    User getUserById(Integer id);
    User getUserByEmail(String email);
    User createUser(RegisterRequest registerRequest);
    User updateUser(Integer id, String name);
    void deleteUser(Integer id);
}
