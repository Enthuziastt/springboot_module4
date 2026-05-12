package com.example.springboot_module4.demo.services;

import com.example.springboot_module4.demo.DTO.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostService {

    List<PostDTO> findAllPosts();

    PostDTO findPostById(Long id);

    PostDTO savePost(PostDTO inputPost);

}
