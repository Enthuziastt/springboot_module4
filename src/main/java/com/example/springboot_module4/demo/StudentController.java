package com.example.springboot_module4.demo;


import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping(path = "/students") public class StudentController {

    @GetMapping(path = "/getStudent/{id}") public StudentResponse getStudentById(@PathVariable Long id) {
        return new StudentResponse(id, "prabhat", "prabhat15032001@gmail.com", "AKTU");
    }

    @PostMapping(path = "/createStudent")
    public StudentResponse createStudent(@RequestBody StudentRequest studentRequest) {
        return new StudentResponse(10L, studentRequest.getName(), studentRequest.getEmail(),
                                   studentRequest.getUniversity());
    }
}
