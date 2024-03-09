package com.cine.verse.Dto.response;

import com.cine.verse.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TmdbMovieDetailsTrailerResponse {
    private boolean adult;
    private String backdrop_path;
    private Object belongs_to_collection;
    private Long budget;
    private Set<Genre> genres;
    private String homepage;
    private Long id;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private List<Map<String, Object>> production_companies;
    private List<Map<String, Object>> production_countries;
    private String release_date;
    private Long revenue;
    private Long runtime;
    private List<Map<String, String>> spoken_languages;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double vote_average;
    private Long vote_count;
    private Map<String, Object> videos;
}
