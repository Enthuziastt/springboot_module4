package com.example.springboot_module4.demo.DTO;


import com.example.springboot_module4.demo.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data public class UserSignUpDto {

    private String name;
    private String email;
    private String password;
    private Set<Role> roles;

}
