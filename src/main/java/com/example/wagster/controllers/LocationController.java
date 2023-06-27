package com.example.wagster.controllers;

import com.example.wagster.models.Location;
import com.example.wagster.models.User;
import com.example.wagster.repos.LocationRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LocationController {

    private final LocationRepo locationDao;

    public LocationController(LocationRepo locationDao) {
        this.locationDao = locationDao;
    }

    @GetMapping("/location/create")
    public String locationCreate(Model model){
        model.addAttribute("location", new Location());
        return "locations/create";
    }

    @PostMapping("/location/create")
    public String locationToDB(@ModelAttribute Location location){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        location.setUser(user);
        locationDao.save(location);
        return "redirect:/feed";
    }
}
