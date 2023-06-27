//package com.example.wagster.controllers;
//
//import com.example.wagster.models.Friend;
//import com.example.wagster.models.User;
//import com.example.wagster.repos.UserRepo;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//public class FriendController {
//
//    private final UserRepo userDao;
//
//    public FriendController(UserRepo userDao) {
//        this.userDao = userDao;
//    }
//
//    @PostMapping("/friend/add")
//    public void addFriend(){
//        Friend friend = new Friend();
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        friend.setUser(user);
//    }
//
//    @GetMapping("/friend/remove")
//}
