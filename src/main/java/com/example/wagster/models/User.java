package com.example.wagster.models;

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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Friend> friends;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Location> locations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Event> events;

    @Column(nullable = false)
    private boolean admin;

    public User(User copy) {
        this.id = copy.id;
        this.email = copy.email;
        this.username = copy.username;
        this.password = copy.password;
    }
    // Constructors

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    private String role;

    public void setRole(String role) {
        this.role = role;
    }


//boolean to check if a user is an administrator or not//

    // Getter and setter for the 'admin' property

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}





