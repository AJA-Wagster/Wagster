package com.example.wagster.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    @OneToOne(cascade = CascadeType.ALL)
    private EventImage eventImage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Event(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
