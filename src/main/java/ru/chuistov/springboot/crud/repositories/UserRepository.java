package ru.chuistov.springboot.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chuistov.springboot.crud.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
