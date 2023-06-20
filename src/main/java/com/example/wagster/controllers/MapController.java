package com.example.wagster.controllers;

import com.example.wagster.models.Location;
import com.example.wagster.repos.LocationRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MapController {
    private final LocationRepo locationRepo;

    public MapController(LocationRepo locationRepo) {
        this.locationRepo = locationRepo;
    }

    @GetMapping("/map")
    public String showMap(Model model) {
        List<Location> locations = locationRepo.findAll();
        model.addAttribute("locations", locations);
        return "locations/map";
    }
}
