package com.java.app.repositories;

import com.java.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("UsersRepository")
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> getUserById(Long id);
    Optional<User> getUserByDisplayName(String displayName);
}
