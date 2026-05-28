package com.example.springboot_module4.demo.repositories;


import com.example.springboot_module4.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository public interface UserRepository extends JpaRepository<User, Long> {

    //    we need to add here the method for fetching user by email
    Optional<User> findByEmail(String email);
}

