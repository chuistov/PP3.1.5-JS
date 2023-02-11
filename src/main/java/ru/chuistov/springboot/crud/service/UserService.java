package ru.chuistov.springboot.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chuistov.springboot.crud.dto.UserDto;
import ru.chuistov.springboot.crud.entities.Role;
import ru.chuistov.springboot.crud.entities.User;
import ru.chuistov.springboot.crud.repositories.UserRepository;
import ru.chuistov.springboot.crud.security.UserDetailsImpl;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final List<Role> allRoles;
    // TODO Get rid of dummy user, use exception instead
    private final User dummyUser = new User("dummy", "dummy", 0, "dummy", "dummy");

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        allRoles = roleService.findAll();
    }

    @Transactional
    public User save(User user) {
        encodePassword(user);
        return userRepository.save(user);
    }

    @Transactional
    public User save(UserDto userDto) {
        User user = new User(userDto, allRoles);
        encodePassword(user);
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

    @Transactional
    public void update(UserDto userDto) {
        User user = new User(userDto, allRoles);
        encodePassword(user);
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<UserDto> findAllDtos() {
        return findAll().stream()
                .map(UserDto::new)
                .toList();
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

    public UserDto getAuthorizedUser() {
        User user = ((UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
        return new UserDto(user);
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}