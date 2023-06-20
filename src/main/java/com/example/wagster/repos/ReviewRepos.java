package com.example.wagster.repos;

import com.example.wagster.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepos extends JpaRepository<Review, Long> {
}
