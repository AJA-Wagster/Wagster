package com.example.wagster.repos;

import com.example.wagster.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepo extends JpaRepository<Review, Long> {
//    void delete(Optional<Review> );
}
