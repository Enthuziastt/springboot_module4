package com.example.springboot_module4.demo.entities;

import com.example.springboot_module4.demo.entities.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
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

        //        we need to also return the permissions along with the roles
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> {
            Set<SimpleGrantedAuthority> permissions = PermissionMapping.getAuthoritiesForRole(role);
            authorities.addAll(permissions);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        });
        return authorities;

    }
    //    we re going to deal with this thing later on

    @Override public @Nullable String getPassword() {
        return this.password;
    }

    @Override public String getUsername() {
        return this.email;
    }
}
