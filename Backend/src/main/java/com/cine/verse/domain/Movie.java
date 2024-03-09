package com.cine.verse.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {

    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer year;

    private String image;

    private String movie_background;

    private String Language;

    private Long budget;

    private String trailer;

    @Column(columnDefinition = "json")
    private String movieData;

    @Column(name = "overview", columnDefinition = "TEXT")
    private String overview;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<Genre> genres;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<WatchList> watchLists;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<MovieList> movieLists;

    public void setObjectData(Object yourObject) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.movieData = objectMapper.writeValueAsString(yourObject);
        } catch (JsonProcessingException e) {
            // Handle the exception according to your application's needs
            e.printStackTrace();
        }
    }

    // Method to get the object by deserializing JSON
    public Object getObjectData(Class<?> valueType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(movieData, valueType);
        } catch (JsonProcessingException e) {
            // Handle the exception according to your application's needs
            e.printStackTrace();
            return null;
        }
    }
}
