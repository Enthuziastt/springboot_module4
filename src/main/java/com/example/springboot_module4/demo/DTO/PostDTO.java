package com.example.springboot_module4.demo.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor public class PostDTO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    private String name;
    private String description;
}
