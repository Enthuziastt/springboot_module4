package com.example.springboot_module4.demo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor public class StudentResponse {

    private Long id;
    private String name;
    private String email;
    private String university;

}
