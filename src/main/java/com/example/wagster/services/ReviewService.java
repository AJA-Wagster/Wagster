package com.example.wagster.services;

import com.example.wagster.models.Review;
import com.example.wagster.repos.ReviewRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepo reviewDao;

    public ReviewService(ReviewRepo reviewDao) {
        this.reviewDao = reviewDao;
    }

    public List<Review> reviewsByLocation(Long id){
        return reviewDao.findReviewsByLocationId(id);
    }
}
