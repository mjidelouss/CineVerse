package com.cine.verse.Dto.response;

import com.cine.verse.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailsTrailer {
    private Long id;
    private String movie_background;
    private List<String> studios;
    private List<Genre> genres;
    private String Language;
    private Long budget;
    private String trailer;
}
