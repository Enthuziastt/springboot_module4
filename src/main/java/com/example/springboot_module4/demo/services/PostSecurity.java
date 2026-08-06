package com.example.springboot_module4.demo.services;


import com.example.springboot_module4.demo.DTO.PostDTO;
import com.example.springboot_module4.demo.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component @RequiredArgsConstructor public class PostSecurity {

    private final PostService postService;


    public boolean isOwnerOfPost(Long postId) {
        User currentAuthenticatedUser =
                (User) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();

        PostDTO postDTO = postService.findPostById(postId);
        assert currentAuthenticatedUser != null;
        return postDTO.getAuthor().getId().equals(currentAuthenticatedUser.getId());

    }
}
