package com.example.wagster.controllers;

import com.example.wagster.repos.LocationRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LocationController {

    private final LocationRepo locationDao;

    public LocationController(LocationRepo locationDao) {
        this.locationDao = locationDao;
    }

    @GetMapping("/location/create")

    @PostMapping("/location/create")
}
