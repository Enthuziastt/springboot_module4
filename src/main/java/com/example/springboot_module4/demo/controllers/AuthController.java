package com.example.springboot_module4.demo.controllers;

import com.example.springboot_module4.demo.DTO.LoginResponseDto;
import com.example.springboot_module4.demo.DTO.UserDto;
import com.example.springboot_module4.demo.DTO.UserLoginDto;
import com.example.springboot_module4.demo.DTO.UserSignUpDto;
import com.example.springboot_module4.demo.services.AuthService;
import com.example.springboot_module4.demo.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;

@RestController @RequestMapping("/auth") @RequiredArgsConstructor public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.env}") private String deployEnv;

    //    this class is going to handle the login aspect

    @PostMapping("/signUp") public ResponseEntity<UserDto> signUp(@RequestBody UserSignUpDto signUpDto) {

        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login") public ResponseEntity<LoginResponseDto> login(@RequestBody UserLoginDto userLoginDto,
                                                                         HttpServletResponse response) {

        LoginResponseDto responseDto = authService.login(userLoginDto);

        Cookie cookie = new Cookie("refreshToken", responseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(deployEnv.equals("production"));
        response.addCookie(cookie);

        return ResponseEntity.ok(responseDto);

    }

    @PostMapping("/refresh") public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request) {
        String refreshToken = Arrays
                .stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("refreshToken not found inside the Cookies"));
        LoginResponseDto responseDto = authService.refreshRequest(refreshToken);
        return ResponseEntity.ok(responseDto);


        //        returned jwt: eyJhbGciOiJIUzI1NiJ9
        //        .eyJzdWIiOiIxIiwiZW1haWwiOiJwcmFiaGF0dHlhZ2kxNTAzMjAwMUBnbWFpbC5jb20iLCJyb2xlcyI6WyJVU0VSIiwiQURNSU4iX
        //        SwiaWF0IjoxNzg0MjA0MjkzLCJleHAiOjE3ODQyMDQ4OTN9.wdBHI3z204HvLkRR4YzzqgNR7kOHtVrQYjUqI5pf92Y

    }

}
