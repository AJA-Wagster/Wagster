package com.example.wagster.repos;

import com.example.wagster.models.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepos extends JpaRepository<Friend, Long> {
}
