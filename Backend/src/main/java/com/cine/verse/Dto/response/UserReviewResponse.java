package com.cine.verse.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserReviewResponse {
    private Long movieId;
    private Long reviewId;
    private String movieImage;
    private Integer year;
    private String movieTitle;
    private String movieLanguage;
    private String content;
    private Integer rating;
    private Boolean liked;
    private LocalDate timestamp;
}
