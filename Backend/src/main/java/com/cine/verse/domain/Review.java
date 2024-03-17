package com.cine.verse.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column()
    private String content;

    @Column()
    private Integer rating;

    @Column()
    private Boolean watched;

    @Column()
    private Boolean liked;

    @Column()
    private Boolean watchlist;

    @Column()
    private LocalDate timestamp;

    @Column(columnDefinition = "integer default 0")
    private Integer totalLikes;

    public void incrementTotalLikes() {
        this.totalLikes++;
    }

    public void decrementTotalLikes() {
        if (this.totalLikes > 0) {
            this.totalLikes--;
        }
    }
}
