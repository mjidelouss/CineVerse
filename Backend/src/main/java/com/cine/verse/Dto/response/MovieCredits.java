package com.cine.verse.Dto.response;
import com.cine.verse.domain.Genre;
import java.util.List;

public class MovieDetails {
    private String movie_background;
    private List<String> studios;
    private List<Genre> genres;
    private String country;
    private String Language;
    private Long budget;
}
