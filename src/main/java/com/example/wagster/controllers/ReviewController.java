package com.example.wagster.controllers;

import com.example.wagster.models.Location;
import com.example.wagster.models.Review;
import com.example.wagster.models.User;
import com.example.wagster.repos.LocationRepo;
import com.example.wagster.repos.ReviewRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {

    private final ReviewRepo reviewDao;
    private final LocationRepo locationDao;

    public ReviewController(ReviewRepo reviewDao, LocationRepo locationDao) {
        this.reviewDao = reviewDao;
        this.locationDao = locationDao;
    }

    @GetMapping("/review/create")
    public String tha(@RequestParam(required = false, name = "locationId") Long id, Model model){
        System.out.println(id);
        model.addAttribute("locationId", id);
        return "locations/location";
    }

    @PostMapping("/review/create")
    public String reviewToDB(@RequestParam(name = "comment") String comment, @RequestParam(name = "rating") short rating, @RequestParam(name = "location") Long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Review review = new Review();
        review.setComment(comment);
        review.setRating(rating);
        review.setUser(user);
        Location location = locationDao.findById(id).get();
        review.setLocation(location);

        reviewDao.save(review);
        return "redirect:/map";
    }

    @GetMapping("/review/{id}/edit")
    public String showReviewForm(@PathVariable long id, Model model){
        Review review = reviewDao.findById(id).get();
        model.addAttribute("review", review);
        return "locations/reviewEdit";
    }
    @PostMapping("/review/{id}/edit")
    public String reviewEditForm(@ModelAttribute Review review){
        reviewDao.save(review);
        return "redirect:/map";
    }


    @PostMapping("/review/{id}/delete")
    public String deleteReview(@PathVariable long id){
        reviewDao.deleteById(id);
        return "redirect:/map";
    }


}
