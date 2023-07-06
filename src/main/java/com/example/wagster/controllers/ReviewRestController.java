package com.example.wagster.controllers;

import com.example.wagster.models.Review;
import com.example.wagster.repos.ReviewRepo;
import com.example.wagster.services.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(headers = "Accept=application/json")
public class ReviewRestController {

    private final ReviewRepo reviewDao;
    private final ReviewService reviewService;

    public ReviewRestController(ReviewRepo reviewDao, ReviewService reviewService) {
        this.reviewDao = reviewDao;
        this.reviewService = reviewService;
    }

    @GetMapping("/review/{locationId}")
    List<Review> reviews(@PathVariable Long locationId){
        return reviewService.reviewsByLocation(locationId);
    }
}
