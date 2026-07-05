package com.example.springboot_module4.demo.controllers;

import com.example.springboot_module4.demo.DTO.UserDto;
import com.example.springboot_module4.demo.DTO.UserLoginDto;
import com.example.springboot_module4.demo.DTO.UserSignUpDto;
import com.example.springboot_module4.demo.services.AuthService;
import com.example.springboot_module4.demo.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/auth") @RequiredArgsConstructor public class AuthController {

    private final UserService userService;

    private final AuthService authService;

    //    this class is going to handle the login aspect

    @PostMapping("/signUp") public ResponseEntity<UserDto> signUp(@RequestBody UserSignUpDto signUpDto) {

        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response) {

        String token = authService.login(userLoginDto);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(token);

    }

}
