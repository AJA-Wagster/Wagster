package com.example.wagster.repos;

import com.example.wagster.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepos extends JpaRepository<Event, Long> {
}
