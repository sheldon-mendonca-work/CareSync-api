package com.caresync.auth_service.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.caresync.auth_service.model.User;
import com.caresync.auth_service.repo.UserRepo;

@Service
public class UserService {
    private UserRepo userRepo;

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }
    public Optional<User> findByEmail(String email){
        return userRepo.findByEmail(email);
    }
}
