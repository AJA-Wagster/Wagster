package com.example.wagster.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(headers = "Accept=application/json")
public class FriendRestController {

    @PostMapping("/friend/add")
    public void addFriend(){

    }
}
