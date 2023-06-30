package com.example.wagster.repos;

import com.example.wagster.models.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepo extends JpaRepository<Friend, Long> {
    List<Friend> findAllByUserId(Long id);
    Friend findByFriendUsername(String username);
}
