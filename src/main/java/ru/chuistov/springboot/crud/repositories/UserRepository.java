package ru.chuistov.springboot.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chuistov.springboot.crud.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
