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
public class ReviewsResponse {
    private Long reviewId;
    private String movieTitle;
    private String userFirstname;
    private Integer rating;
    private LocalDate timestamp;
}
