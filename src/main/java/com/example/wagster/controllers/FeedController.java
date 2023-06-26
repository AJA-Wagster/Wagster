package com.example.wagster.controllers;

import com.example.wagster.models.Post;
import com.example.wagster.models.User;
import com.example.wagster.repos.PostRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.wagster.models.Event;
import com.example.wagster.repos.EventRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;

@Controller
public class FeedController {

    private final EventRepo eventsDao;
    private final PostRepo postDao;


    public FeedController(EventRepo eventsDao, PostRepo postsDao) {
        this.eventsDao = eventsDao;
        this.postDao = postsDao;
    }

    @GetMapping("/feed")
    public String showFeed(Model model) {

//        get the events from the event service
        List<Event> events = eventsDao.findAll();
//        get the posts from the post service
        List<Post> posts = postDao.findAll();

//        add events and posts to the feed
        model.addAttribute("events", events);
        model.addAttribute("posts", posts);


        return "posts/feed";
    }

    @GetMapping("/posts/create")
    public String showPostCreateForm(Model model){
        model.addAttribute("post" ,new Post());
        return "posts/create";
    }

//    @PostMapping("/posts/create")
//    public String moveToDB(@ModelAttribute Post post, @RequestParam("file")MultipartFile file) throws IOException {
//        byte[] imageData = file.getBytes();
//        post.setPostImage(imageData);
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        post.setUser(user);
//        postDao.save(post);
//        return "redirect:/feed";
//    }
}

