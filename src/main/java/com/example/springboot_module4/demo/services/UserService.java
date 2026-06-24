package com.example.springboot_module4.demo.services;


import com.example.springboot_module4.demo.exceptions.ResourceNotFoundException;
import com.example.springboot_module4.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service @NoArgsConstructor @AllArgsConstructor public class UserService implements UserDetailsService {
//
//    private UserRepository userRepository;
//
//    @Override public UserDetails loadUserByUsername(String username) {
//        return this.userRepository.findByEmail(username).orElseThrow(
//                () -> new ResourceNotFoundException("could not find any user with specified name"));
//    }
//}