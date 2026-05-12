package com.example.springboot_module4.demo.advices;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDate;

@Data public class ApiError {

    private LocalDate dateTimeNow;
    private String error;
    private HttpStatusCode statusCode;

    public ApiError() {
        this.dateTimeNow = LocalDate.now();
    }

    public ApiError(String error,
                    HttpStatusCode statusCode) {
        this.error = error;
        this.statusCode = statusCode;
    }
}
