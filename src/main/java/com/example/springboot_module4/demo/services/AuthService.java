package com.example.springboot_module4.demo.services;

import com.example.springboot_module4.demo.DTO.LoginResponseDto;
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
    private final UserService userService;
    private final SessionService sessionService;

    public LoginResponseDto login(UserLoginDto userLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));

        User authenticatedUser = (User) authentication.getPrincipal();

        if (authenticatedUser != null) {

            String accessToken = jwtService.generateAccessToken(authenticatedUser);
            String refreshToken = jwtService.generateRefreshToken(authenticatedUser);

            //            storing a new session here too
            sessionService.generateNewSession(authenticatedUser, refreshToken);

            return new LoginResponseDto(authenticatedUser.getId(), accessToken, refreshToken);
        } else {
            throw new BadCredentialsException("user could not be authenticated");
        }
    }

    public LoginResponseDto refreshRequest(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userService.getUserById(userId);
        //        before granting another access token, we must check if the access token is valid or not
        sessionService.validateSession(refreshToken);

        String accessToken = jwtService.generateAccessToken(user);
        return new LoginResponseDto(userId, accessToken, refreshToken);

    }
}
