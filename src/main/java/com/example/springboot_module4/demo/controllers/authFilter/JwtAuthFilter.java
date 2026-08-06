package com.example.springboot_module4.demo.controllers.authFilter;

import com.example.springboot_module4.demo.entities.User;
import com.example.springboot_module4.demo.services.JwtService;
import com.example.springboot_module4.demo.services.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component @RequiredArgsConstructor @Slf4j public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired @Qualifier("handlerExceptionResolver") private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer")) {
                filterChain.doFilter(request, response);
            } else {
                String tokenString = token.split("Bearer ")[1];
                Long userId = jwtService.getUserIdFromToken(tokenString);
                User user = userService.getUserById(userId);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                log.info("Before continuing chain, context has authentication: {}",
                         SecurityContextHolder.getContext().getAuthentication());
                filterChain.doFilter(request, response);

            }
        } catch (JwtException exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
