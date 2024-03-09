package com.cine.verse.Dto.response;

import com.cine.verse.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailsTrailer {
    private Long id;
    private String movie_background;
    private Set<Genre> genres;
    private String Language;
    private Long budget;
    private String trailer;
}
