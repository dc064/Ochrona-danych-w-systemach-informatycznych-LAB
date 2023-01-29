package com.noteapp.pwlab.noteapp.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.noteapp.pwlab.noteapp.user.entities.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findFirstByUsernameIgnoreCase(String username);

	Optional<User> findFirstByEmailIgnoreCase(String email);
}
