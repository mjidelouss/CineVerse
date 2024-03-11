package com.cine.verse.Dto.response;

import com.cine.verse.domain.Like;
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
    private String firstname;
    private String lastname;
    private String image;
    private String content;
    private Integer rating;
    private LocalDate timestamp;
    private Set<Like> likes;
}
