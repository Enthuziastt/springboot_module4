package com.example.springboot_module4.demo.entities;

import com.example.springboot_module4.demo.entities.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity @Table(name = "users") @NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder public class User
        implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String email;
    private String password;
    private String name;

    @ElementCollection(fetch = FetchType.EAGER) @Enumerated(EnumType.STRING) private Set<Role> roles;


    @Override public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.name())))
                .collect(Collectors.toSet());
    }
    //    we re going to deal with this thing later on

    @Override public @Nullable String getPassword() {
        return this.password;
    }

    @Override public String getUsername() {
        return this.email;
    }
}
