package com.example.wagster.repos;

import com.example.wagster.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post,Long> {

    Post findByTitle(String postToBeDeleted);
}
