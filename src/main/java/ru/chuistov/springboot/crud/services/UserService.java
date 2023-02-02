package ru.chuistov.springboot.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chuistov.springboot.crud.dto.UserDto;
import ru.chuistov.springboot.crud.entities.User;
import ru.chuistov.springboot.crud.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final User dummyUser = new User("dummy", "dummy", 0, "dummy", "dummy");

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public String getUserPassword(UserDto userDto) {
        long id = userDto.getId();
        return userRepository
                .findById(id)
                .orElse(dummyUser)
                .getPassword();
    }
}