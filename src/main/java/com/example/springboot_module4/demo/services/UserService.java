package com.example.springboot_module4.demo.services;


import com.example.springboot_module4.demo.DTO.UserDto;
import com.example.springboot_module4.demo.DTO.UserSignUpDto;
import com.example.springboot_module4.demo.entities.User;
import com.example.springboot_module4.demo.exceptions.ResourceNotFoundException;
import com.example.springboot_module4.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service @RequiredArgsConstructor public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override public UserDetails loadUserByUsername(String username) {
        return this.userRepository
                .findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("could not find any user with these credentials"));
    }

    public User getUserById(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("could not fetch user " + "with this id"));
    }

    public UserDto signUp(UserSignUpDto signUpDto) {
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
        if (user.isPresent()) {
            throw new BadCredentialsException("user already exists with email: " + signUpDto.getEmail());
        }

        User userToBeCreated = modelMapper.map(signUpDto, User.class);
        userToBeCreated.setPassword(passwordEncoder.encode(userToBeCreated.getPassword()));

        User savedUser = userRepository.save(userToBeCreated);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }

    public User saveWithoutPassword(User user) {
        return this.userRepository.save(user);
    }

}