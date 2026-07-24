package com.example.springboot_module4.demo.configs;


import com.example.springboot_module4.demo.controllers.SuccessHandlers.OAuth2SuccessHandler;
import com.example.springboot_module4.demo.controllers.authFilter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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

import static com.example.springboot_module4.demo.entities.enums.Role.ADMIN;
import static com.example.springboot_module4.demo.entities.enums.Role.CREATOR;

@Configuration @EnableWebSecurity
// with the help of this annotation, am able to tell
// spring that i am going to provide back the SecurityFilterChain Object it expects
@RequiredArgsConstructor public class WebSecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private final String[] publicRoutes = {"/error", "/auth/**", "/home.html"};

    @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/post/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/post/**")
                        .hasAnyRole(ADMIN.name(), CREATOR.name())
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
