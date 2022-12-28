package ru.chuistov.springboot.crud.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.chuistov.springboot.crud.entities.User;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public User add(User user) {
        return entityManager.merge(user);
    }

    @Transactional
    public void delete(long id) {
        entityManager.remove(get(id));
    }

    public User get(long id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    public List<User> getAll() {
        return entityManager.createQuery("from User").getResultList();
    }
}