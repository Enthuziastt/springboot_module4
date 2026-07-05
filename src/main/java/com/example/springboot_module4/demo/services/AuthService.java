package com.example.springboot_module4.demo.services;

import com.example.springboot_module4.demo.DTO.UserDto;
import com.example.springboot_module4.demo.DTO.UserLoginDto;
import com.example.springboot_module4.demo.DTO.UserSignUpDto;
import com.example.springboot_module4.demo.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String login(UserLoginDto userLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));

        User authenticatedUser = (User) authentication.getPrincipal();

        if (authenticatedUser != null) {
            return jwtService.generateToken(authenticatedUser);
        } else {
            throw new BadCredentialsException("user could not be authenticated");
        }
    }
}
