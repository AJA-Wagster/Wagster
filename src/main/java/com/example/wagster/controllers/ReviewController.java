package com.example.wagster.controllers;

import com.example.wagster.models.Location;
import com.example.wagster.models.Review;
import com.example.wagster.models.User;
import com.example.wagster.repos.LocationRepo;
import com.example.wagster.repos.ReviewRepo;
import com.example.wagster.repos.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;

@Controller
public class ReviewController {

    private final ReviewRepo reviewDao;
    private final LocationRepo locationDao;
    private final UserRepo userDao;

    public ReviewController(ReviewRepo reviewDao, LocationRepo locationDao, UserRepo userDao) {
        this.reviewDao = reviewDao;
        this.locationDao = locationDao;
        this.userDao = userDao;
    }

    @GetMapping("/review/create/{id}")
    public String tha(Model model, @PathVariable(name = "id") Long id, @RequestParam(name = "username", required = false) String username){
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(username);
        model.addAttribute("locationId", id);
        model.addAttribute("username", username);
        return "locations/location";
    }

    @PostMapping("/review/create")
    public String reviewToDB(@RequestParam(name = "comment") String comment, @RequestParam(name = "rating") short rating, @RequestParam(name = "location") Long id, @RequestParam(name = "user") String username){
        Review review = new Review();
        System.out.println(username);
        review.setComment(comment);
        review.setRating(rating);
        review.setUser(userDao.findByUsername(username));
        Location location = locationDao.findById(id).get();
        review.setLocation(location);
        reviewDao.save(review);

        List<Review> reviews = reviewDao.findAllByLocationId(id);
        int total = 0;
        for (int i = 0; i < reviews.size(); i++){
            total += reviews.get(i).getRating();
        }
        float average = (float) total / reviews.size();
        average = (float) (Math.round(average * 100.0) / 100);
        System.out.println(average);
        location.setRating(average);
        locationDao.save(location);
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
