package com.example.wagster.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

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

    @Column(nullable = false, columnDefinition = "LONGBLOB")
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private Blob file;

    @Column(nullable = false)
    private String description;

    @OneToOne
    private Location location;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Event(String title, Blob file, String description) {
        this.title = title;
        this.file = file;
        this.description = description;
    }
}
