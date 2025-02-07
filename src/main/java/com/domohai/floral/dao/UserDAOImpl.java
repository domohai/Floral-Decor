package com.domohai.floral.dao;

import com.domohai.floral.entity.BUser;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private EntityManager entityManager;
    
    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public void save(BUser user) {
    }

    @Override
    public List<BUser> findAll() {
        return entityManager.createQuery("FROM BUser", BUser.class).getResultList();
    }
    
    @Override
    public BUser findById(Integer id) {
        return entityManager.find(BUser.class, id);
    }
}
