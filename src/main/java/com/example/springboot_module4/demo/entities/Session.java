package com.example.springboot_module4.demo.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity @AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder public class Session {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @ManyToOne private User user;

    @CreationTimestamp private LocalDate lastUsedAt;

    private String refreshToken;
}
