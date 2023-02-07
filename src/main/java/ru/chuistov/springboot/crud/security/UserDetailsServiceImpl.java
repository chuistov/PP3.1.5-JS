package ru.chuistov.springboot.crud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.chuistov.springboot.crud.entities.User;
import ru.chuistov.springboot.crud.repositories.UserRepository;
import ru.chuistov.springboot.crud.security.UserDetailsImpl;

import java.util.Optional;

// security needs
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with such email not found");
        }

        return new UserDetailsImpl(user.get());
    }
}
