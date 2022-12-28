package ru.chuistov.springboot.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chuistov.springboot.crud.entities.User;
import ru.chuistov.springboot.crud.repositories.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final User dummyUser = new User("no name", "no last name", 0);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public User findById(long id) {
        return userRepository.findById(id)
                .orElse(dummyUser);
    }

    @Transactional
    public void update(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}