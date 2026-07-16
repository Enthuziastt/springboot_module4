package com.example.springboot_module4.demo.controllers.SuccessHandlers;

import com.example.springboot_module4.demo.entities.User;
import com.example.springboot_module4.demo.services.JwtService;
import com.example.springboot_module4.demo.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component @Slf4j @RequiredArgsConstructor public class OAuth2SuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtService jwtService;

    @Value("${deploy.env}") private String deployEnv;

    @Override public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                  Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User auth2User = (DefaultOAuth2User) authToken.getPrincipal();
        //        fetching the user out of the token

        String authUserEmail = auth2User.getAttribute("email");
        User fetchedUser = userService.getUserByEmail(authUserEmail);

        if (fetchedUser == null) {
            //            we need to register a new user this time
            User user = User.builder().name(auth2User.getAttribute("name")).email(authUserEmail).build();
            fetchedUser = userService.saveWithoutPassword(user);
            logger.info("its a new user that is saved: " + fetchedUser);
        }


        //        need to now create access and refresh token for the same
        String refreshToken = jwtService.generateRefreshToken(fetchedUser);
        String accessToken = jwtService.generateAccessToken(fetchedUser);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(deployEnv.equals("production"));

        response.addCookie(cookie);
        String frontEndUrl = "http://localhost:8080/home.html?token=" + accessToken;
        response.sendRedirect(frontEndUrl);
    }

}
