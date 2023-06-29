package com.example.wagster.controllers;

import com.example.wagster.models.User;
import com.example.wagster.repos.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private final UserRepo userDao;

    public ProfileController(UserRepo userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedIn = userDao.findById(user.getId()).get();
//        System.out.println(loggedIn.getImageURL());
//        if (loggedIn.getImageURL() == null) {
//            loggedIn.setImageURL("j");
//        }
        model.addAttribute("user", loggedIn);
        return "users/profile";
    }
}
