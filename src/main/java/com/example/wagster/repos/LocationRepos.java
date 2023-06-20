package com.example.wagster.repos;

import com.example.wagster.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepos extends JpaRepository<Location, Long> {
}
