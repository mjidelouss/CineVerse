package com.cine.verse.Dto.response;

import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Like;
import com.cine.verse.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private AppUser appUser;
    private Movie movie;
    private String content;
    private Integer rating;
    private Boolean watched;
    private LocalDate timestamp;
    private Set<Like> likes;
}
