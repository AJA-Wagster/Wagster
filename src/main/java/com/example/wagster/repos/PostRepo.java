package com.example.wagster.repos;

import com.example.wagster.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Long> {
    List<Post> findByOrderByIdDesc();
    List<Post> findAllByUserId(Long id);
}
