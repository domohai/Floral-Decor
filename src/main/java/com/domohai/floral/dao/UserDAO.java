package com.domohai.floral.dao;

import com.domohai.floral.entity.BUser;

import java.util.List;

public interface UserDAO {
    void save(BUser user);
    List<BUser> findAll();
    BUser findById(Integer id);
}
