package com.example.springboot_module4.demo.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor public class LoginResponseDto {

    private Long userId;
    private String accessToken;
    private String refreshToken;
}
