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
public class PopularReviewResponse {
    private Long movieId;
    private Long userId;
    private String movieImage;
    private String userImage;
    private String movieTitle;
    private Integer movieYear;
    private String userFirstname;
    private String userLastname;
    private Integer rating;
    private String content;
    private LocalDate timestamp;


}
