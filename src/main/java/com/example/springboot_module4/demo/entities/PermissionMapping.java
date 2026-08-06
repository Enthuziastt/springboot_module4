package com.example.springboot_module4.demo.entities;

import com.example.springboot_module4.demo.entities.enums.Permission;
import com.example.springboot_module4.demo.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.springboot_module4.demo.entities.enums.Role.*;

public class PermissionMapping {

    private static final Map<Role, Set<Permission>> map =
            Map.of(USER, Set.of(Permission.USER_VIEW, Permission.POST_VIEW), CREATOR,
                   Set.of(Permission.POST_CREATE, Permission.USER_UPDATE, Permission.POST_UPDATE), ADMIN,
                   Set.of(Permission.POST_CREATE, Permission.USER_UPDATE, Permission.POST_UPDATE,
                          Permission.USER_DELETE, Permission.USER_CREATE, Permission.POST_DELETE));

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role) {
        return map
                .get(role)
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }


}
