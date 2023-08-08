package com.example.wagster.services;

import com.example.wagster.models.User;
import com.example.wagster.repos.FriendRepo;
import com.example.wagster.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService {

    private final UserRepo userRepo;
    private final FriendRepo friendRepo;

    @Autowired
    public FriendService(UserRepo userRepo, FriendRepo friendRepo) {
        this.userRepo = userRepo;
        this.friendRepo = friendRepo;
    }
}
