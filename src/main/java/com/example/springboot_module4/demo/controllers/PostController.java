package com.example.springboot_module4.demo.controllers;

import com.example.springboot_module4.demo.DTO.PostDTO;
import com.example.springboot_module4.demo.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping(path = "/post") public class PostController {
    //rest controller bascially tells us that this class is going to handle
    //    the api responses also the output to called will be in format json/xml

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(path = "/getAllPosts") public List<PostDTO> getAllPosts() {
        return this.postService.findAllPosts();
    }

    @GetMapping(path = "/getPostById/{postId}") public PostDTO getPostById(@PathVariable Long postId) {
        return this.postService.findPostById(postId);
    }

    @PostMapping(path = "/savePost") public PostDTO savePost(@RequestBody PostDTO postDTO) {
        return this.postService.savePost(postDTO);
    }
}
