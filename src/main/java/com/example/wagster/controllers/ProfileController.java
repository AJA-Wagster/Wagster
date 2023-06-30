package com.example.wagster.controllers;

import com.example.wagster.models.User;
import com.example.wagster.repos.FriendRepo;
import com.example.wagster.repos.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfileController {

    private final UserRepo userDao;
    private final FriendRepo friendDao;

    public ProfileController(UserRepo userDao, FriendRepo friendDao) {
        this.userDao = userDao;
        this.friendDao = friendDao;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedIn = userDao.findById(user.getId()).get();
//        System.out.println(loggedIn.getImageURL());
//        if (loggedIn.getImageURL() == null) {
//            loggedIn.setImageURL("j");
//        }
        List<User> userList = userDao.findAll();
        model.addAttribute("user", loggedIn);
        model.addAttribute("usersList", userList);
        model.addAttribute("friends", friendDao.findAllByUserId(loggedIn.getId()));
        return "users/profile";
    }
}
