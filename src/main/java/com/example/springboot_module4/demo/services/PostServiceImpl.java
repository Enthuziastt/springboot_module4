package com.example.springboot_module4.demo.services;

import com.example.springboot_module4.demo.DTO.PostDTO;
import com.example.springboot_module4.demo.entities.PostEntity;
import com.example.springboot_module4.demo.exceptions.ResourceNotFoundException;
import com.example.springboot_module4.demo.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


// we need to keep in mind that, we need to declare the implementation class as the bean because it is excatly the code
// which needs to run and execute -> an interface is just a contract and is not instantiated.

// and i should try to find answers to these questions first by myself -> go to internet or otherwise you will not
// develop that thinking needed to resolve issues and get out of situations
@Service public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        //        this is an illustration of constructor injection
    }

    @Override public List<PostDTO> findAllPosts() {

        return this.postRepository.findAll().stream().map(postEntity -> this.modelMapper.map(postEntity, PostDTO.class))
                                  .collect(Collectors.toList());

    }

    @Override public PostDTO findPostById(Long postId) {
        return this.modelMapper.map(this.postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("could not find the post with this id")), PostDTO.class);
    }

    @Override public PostDTO savePost(PostDTO inputPost) {

        return this.modelMapper.map(this.postRepository.save(this.modelMapper.map(inputPost, PostEntity.class)),
                                    PostDTO.class);
        //        so this is going to first convert post dto -> entity
        //        and get back entity -> map it back to DTO
    }
}
