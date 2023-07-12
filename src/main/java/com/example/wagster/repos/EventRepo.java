package com.example.wagster.repos;

import com.example.wagster.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {
    List<Event> findAllByOrderByIdDesc();
    List<Event> findAllByUserId(Long id);
}
