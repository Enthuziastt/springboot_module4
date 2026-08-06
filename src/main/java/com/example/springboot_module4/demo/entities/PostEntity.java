package com.example.springboot_module4.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor public class PostEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY) private User author;

}
