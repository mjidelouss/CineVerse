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
public class RecentUserReviewResponse {
    private Long movieId;
    private String movieImage;
    private String movieTitle;
    private Integer movieYear;
    private Integer rating;
    private String content;
    private LocalDate timestamp;
}
