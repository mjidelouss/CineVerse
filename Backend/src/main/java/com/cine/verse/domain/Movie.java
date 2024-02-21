package com.cine.verse.domain;

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
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer year;

    private String director;

    private String image;

    @Column(name = "overview", columnDefinition = "TEXT")
    private String overview;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<WatchList> watchLists;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<MovieList> movieLists;
}
