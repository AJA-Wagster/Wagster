package com.example.wagster.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private  String name;

    @Column(nullable = false)
    private  String address;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private short rating;

    @ManyToOne
    @JoinColumn( name = "user_id")
    @JsonBackReference
    private User user;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "location")
    private List<Review> reviews;
}
