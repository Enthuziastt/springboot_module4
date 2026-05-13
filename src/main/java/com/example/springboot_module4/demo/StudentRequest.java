package com.example.springboot_module4.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor public class StudentRequest {

    private String name;
    private String email;
    private String university;

}
