package com.example.springboot_module4.demo.configs;


import com.example.springboot_module4.demo.controllers.SuccessHandlers.OAuth2SuccessHandler;
import com.example.springboot_module4.demo.controllers.authFilter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.http.HttpRequest;
import java.util.List;

import static com.example.springboot_module4.demo.entities.enums.Permission.POST_VIEW;
import static com.example.springboot_module4.demo.entities.enums.Role.ADMIN;
import static com.example.springboot_module4.demo.entities.enums.Role.CREATOR;

@Configuration @EnableWebSecurity @EnableMethodSecurity(securedEnabled = true) @RequiredArgsConstructor @Slf4j
public class WebSecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private final String[] publicRoutes = {"/auth/**", "/home.html"};

    @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes)
                        .permitAll()
                        .requestMatchers("/post/**")
                        .authenticated()
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(
                        oAuthConfig -> oAuthConfig.failureUrl("/login?error=true").successHandler(oAuth2SuccessHandler))
                //                .formLogin(Customizer.withDefaults())

                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

}

//    "userId": 1,
//            "accessToken": "eyJhbGciOiJIUzI1NiJ9
//            .eyJzdWIiOiIxIiwiZW1haWwiOiJuZWhhLnZlcm1hQGV4YW1wbGUuY29tIiwicm9sZXMiOiJbVVNFUl0iLCJpYXQiOjE3ODU4NTI2MTMsImV4cCI6MTc4NTg1MzIxM30.oECwBXep81yHnwNPHpnrpRgtIFeBKynZvNbRbz2SN70",
//            "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzg1ODUyNjEzLCJleHAiOjE4MDE0MDQ2MTN9
//            .SAn3m_Ek3pnfhTeoj-_KR_aXyX9O0yR4f3Hb43paSyo"