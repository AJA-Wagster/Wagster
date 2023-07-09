package com.example.wagster.controllers;

import com.example.wagster.models.User;
import com.example.wagster.repos.EventRepo;
import com.example.wagster.repos.FriendRepo;
import com.example.wagster.repos.PostRepo;
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
    private final PostRepo postDao;
    private final EventRepo eventDao;

    public ProfileController(UserRepo userDao, FriendRepo friendDao, PostRepo postDao, EventRepo eventDao) {
        this.userDao = userDao;
        this.friendDao = friendDao;
        this.postDao = postDao;
        this.eventDao = eventDao;
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
        model.addAttribute("posts", postDao.findAllByUserId(user.getId()));
        model.addAttribute("events", eventDao.findAllByUserId(user.getId()));
        return "users/profile";
    }
}
