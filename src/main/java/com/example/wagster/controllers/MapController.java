package com.example.wagster.controllers;

import com.example.wagster.models.Location;
import com.example.wagster.models.User;
import com.example.wagster.repos.LocationRepo;
import com.example.wagster.repos.ReviewRepo;
import com.example.wagster.repos.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
//private final LocationRepo locationDao;

@Controller
public class MapController {
    private final LocationRepo locationDao;
    private final ReviewRepo reviewDao;
    private final UserRepo userDao;

    public MapController(LocationRepo locationDao, ReviewRepo reviewDao, UserRepo userDao) {
        this.locationDao = locationDao;
        this.reviewDao = reviewDao;
        this.userDao = userDao;
    }

    @GetMapping("/map")
    public String showMap(Model model) {
        List<Location> locations = locationDao.findAll();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("locations", locations);
        if(user != null){
            System.out.println(user.isAdmin());
            model.addAttribute("userAdmin", userDao.findById(user.getId()).get());
        }
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
    @GetMapping("location/{id}/edit")
    public String sr(Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("location", locationDao.findById(id).get());
        return "locations/locationEdit";
    }

    @PostMapping("location/{id}/edit")
    public String tr(@ModelAttribute Location location){
        locationDao.save(location);
        return "redirect:/map";
    }

    @PostMapping("/location/{id}/delete")
    public String deleteLocation(@PathVariable(name = "id") Long id){
        locationDao.deleteById(id);
        return "redirect:/map";
    }
}
