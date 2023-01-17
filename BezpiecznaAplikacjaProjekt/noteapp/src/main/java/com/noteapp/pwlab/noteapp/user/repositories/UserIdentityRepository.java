package com.noteapp.pwlab.noteapp.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noteapp.pwlab.noteapp.user.entities.UserIdentity;

public interface UserIdentityRepository extends JpaRepository<UserIdentity, Long> {
    
}
