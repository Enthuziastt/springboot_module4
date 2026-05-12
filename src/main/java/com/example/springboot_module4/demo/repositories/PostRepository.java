package com.example.springboot_module4.demo.repositories;

import com.example.springboot_module4.demo.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository public interface PostRepository extends JpaRepository<PostEntity, Long> {

}
