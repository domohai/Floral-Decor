package com.domohai.floral.old.service.service.user;

import com.domohai.floral.old.controller.user.RegisterRequest;
import com.domohai.floral.old.model.User;

public interface IUserService {
    User getUserById(Integer id);
    User getUserByEmail(String email);
    User createUser(RegisterRequest registerRequest);
    User updateUser(Integer id, String name);
    void deleteUser(Integer id);
}
