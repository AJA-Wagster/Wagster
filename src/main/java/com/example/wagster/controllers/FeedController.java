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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        return "posts/postCreate";
    }



    @PostMapping("/posts/create")
    public String moveToDB(@ModelAttribute Post post){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(user);
        postDao.save(post);
        return "redirect:/feed";
    }

    @GetMapping("/posts/{id}/edit")
    public String showPostEditForm(@PathVariable("id") Long id, Model model){
        Post post = postDao.findById(id).orElse(null);
        model.addAttribute("post", post);
        return "posts/postEdit";
    }

    @PostMapping("/posts/{id}/edit")
    public String updatePostDB(@PathVariable("id") Long id, @ModelAttribute Post updatedPost){
        Post post = postDao.findById(id).orElse(null);
        if (post != null) {
            post.setTitle(updatedPost.getTitle());
            post.setBody(updatedPost.getBody());
            // Update other properties as needed
            postDao.save(post);
        }
        return "redirect:/feed";
    }

    @PostMapping("/posts/{id}/delete")
    public String removePostFromDB(@PathVariable("id") Long id){
        postDao.deleteById(id);
        return "redirect:/feed";
    }

    @GetMapping("/events/create")
    public String showCreateEventForm(Model model){
        model.addAttribute("event", new Event());
        return "posts/eventCreate";
    }

    @PostMapping("/events/create")
    public String moveEventToDB(@ModelAttribute Event event){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        event.setUser(user);
        return "redirect:/feed";
    }

    @GetMapping("/events/{id}/edit")
    public String showEventEditForm(){
        return "posts/eventEdit";
    }

    @PostMapping("/events/{id}/edit")
    public String updateEventDB(){
        return "redirect:/feed";
    }

    @PostMapping("/events/{id}/delete")
    public String removeEventFromDB(){
        return "redirect:/feed";
    }

}

