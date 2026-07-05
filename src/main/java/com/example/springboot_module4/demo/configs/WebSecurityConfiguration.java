package com.example.springboot_module4.demo.configs;


import com.example.springboot_module4.demo.controllers.authFilter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

@Configuration @EnableWebSecurity
// with the help of this annotation, am able to tell
// spring that i am going to provide back the SecurityFilterChain Object it expects
@RequiredArgsConstructor public class WebSecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;

    //    this is a configuration class, and it returns a password encoder

//    @Bean UserDetailsService userDetailsServiceProvider() {
//        UserDetails user1 = User
//                .withUsername("prabhat")
//                .password(passwordEncoder().encode("Prabhat@123"))
//                .authorities("ADMIN")
//                .build();
//
//        UserDetails user2 =
//                User.withUsername("aman").password(passwordEncoder().encode("Aman@123")).authorities("USER").build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

    @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/post", "/error", "/auth/**")
                        .permitAll()
                        .requestMatchers("/getAllPosts/**")
                        .hasAnyRole("ADMIN")
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                //                .formLogin(Customizer.withDefaults())

                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
