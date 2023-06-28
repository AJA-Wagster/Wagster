package com.example.wagster.controllers;

import com.example.wagster.repos.ReviewRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {

    private final ReviewRepo reviewDao;

    public ReviewController(ReviewRepo reviewDao) {
        this.reviewDao = reviewDao;
    }

    @PostMapping("/review/create")
    public String reviewToDB(){
        return "locations/map";
    }
    @GetMapping("/review/{id}/edit")
    public String showReviewForm(){
        return "locations/reviewEdit";
    }
    @PostMapping("/review/{id}/edit")
    public String reviewEditForm(){
        return "redirect:/map";
    }
    @PostMapping("/review/{id}/delete")
    public String deleteReview(Long id){
        reviewDao.delete(reviewDao.findById(id));
        return "redirect:/map";
    }
}
