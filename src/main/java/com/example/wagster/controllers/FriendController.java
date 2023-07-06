package com.example.wagster.controllers;

import com.example.wagster.models.Friend;
import com.example.wagster.models.User;
import com.example.wagster.repos.FriendRepo;
import com.example.wagster.repos.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FriendController {

    private final UserRepo userDao;
    private final FriendRepo friendDao;


    public FriendController(UserRepo userDao, FriendRepo friendDao) {
        this.userDao = userDao;
        this.friendDao = friendDao;
    }

    @PostMapping("/friend/add")
    public String addFriend(@RequestParam(name = "user") String username){
        Friend friend = new Friend();
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        friend.setUser(currentUser);
//        friend.setFriend(userDao.findByUsername(username));
//        System.out.println(friend.getFriend().getUsername() + friend.getUser().getUsername());
        friendDao.save(friend);
        return "redirect:/feed";
    }

    @PostMapping("/friend/remove")
    public String removeFriend(@RequestParam(name = "friend") long id){
        Friend friend = friendDao.findById(id).get();
        friendDao.delete(friend);
        return "redirect:/feed";
    }
}
