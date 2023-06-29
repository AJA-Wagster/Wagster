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

<<<<<<< HEAD
    @Column
    private String imageURL;
=======
//    @OneToOne(cascade = CascadeType.ALL)
//    private Location location;
>>>>>>> dfeb108db103ce45bde1e11b6bdf3b9eb1a9ccf2

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Event(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Event(String title, String description, String imageURL) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
    }
}
