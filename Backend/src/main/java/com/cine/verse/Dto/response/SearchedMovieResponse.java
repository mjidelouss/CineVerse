package com.cine.verse.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchedMovieResponse {
    private Long movieId;
    private String movieImage;
    private String movieTitle;
    private Integer movieYear;
}
