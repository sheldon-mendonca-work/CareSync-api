package com.caresync.auth_service.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caresync.auth_service.model.User;


@Repository
public interface UserRepo extends JpaRepository<User, UUID>{
    Optional<User> findByEmail(String email);
}
