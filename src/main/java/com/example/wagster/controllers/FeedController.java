package com.example.wagster.controllers;

import com.example.wagster.models.Event;
import com.example.wagster.models.Post;
import com.example.wagster.repos.EventRepo;
import com.example.wagster.repos.PostRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//@Controller
//public class FeedController {
//

//}

@Controller
public class FeedController {

    private final EventRepo eventsDao;
    private PostRepo postsDao;

    public FeedController(EventRepo eventsDao, PostRepo postsDao) {
        this.eventsDao = eventsDao;
        this.postsDao = postsDao;
    }

    @GetMapping("/feed")
    public String showFeed(Model model) {

//        get the events from the event service
        List<Event> events = eventsDao.findAll();
//        get the posts from the post service
        List<Post> posts = postsDao.findAll();

//        add events and posts to the feed
        model.addAttribute("events", events);
        model.addAttribute("posts", posts);


        return "posts/feed";
    }
}

