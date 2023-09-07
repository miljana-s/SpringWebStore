package com.example.springproject.repository;

import com.example.springproject.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByUsernameAndPassword(String username, String password);

    Optional<UserModel> findFirstByUsername(String username);
}
