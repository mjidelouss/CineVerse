package com.cine.verse.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String image;
    private String bio;
    private String location;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<Like> likes;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<WatchList> watchLists;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<MyList> myLists;
}

