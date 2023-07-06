package com.example.wagster.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column
    private String imageURL;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Friend> friends;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Location> locations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Event> events;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private byte admin;

    public User(User copy) {
        this.id = copy.id;
        this.email = copy.email;
        this.username = copy.username;
        this.password = copy.password;
        this.imageURL = copy.imageURL;
    }
    // Constructors

    public User(String username, String email, String password, String imageURL) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.imageURL = imageURL;
    }

//    private String role;
//
//    public void setRole(String role) {
//        this.role = role;
//    }


//boolean to check if a user is an administrator or not//

    // Getter and setter for the 'admin' property

    public boolean isAdmin() {
        return admin !=0;
    }

    public void setAdmin(byte admin) {
        this.admin = admin;
    }

}





