package com.example.wagster.controllers;

import com.example.wagster.models.Post;
import com.example.wagster.models.User;
import com.example.wagster.repos.PostRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class FeedController {

    private final PostRepo postDao;

    public FeedController(PostRepo postDao){
        this.postDao = postDao;
    }

    @GetMapping("/feed")
    public String profile() {
        return "posts/feed";
    }

    @GetMapping("/posts/create")
    public String showPostCreateForm(Model model){
        model.addAttribute("post" ,new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String moveToDB(@ModelAttribute Post post) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(user);
        postDao.save(post);
        return "redirect:/feed";
    }
}
