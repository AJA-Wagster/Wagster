package com.example.wagster.controllers;

import com.example.wagster.models.Location;
import com.example.wagster.models.Review;
import com.example.wagster.models.User;
import com.example.wagster.repos.LocationRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
//private final LocationRepo locationDao;

@Controller
public class MapController {
    private final LocationRepo locationDao;

    public MapController(LocationRepo locationDao) {
        this.locationDao = locationDao;
    }

    @GetMapping("/map")
    public String showMap(Model model) {
        List<Location> locations = locationDao.findAll();
        model.addAttribute("locations", locations);
        return "locations/map";
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
