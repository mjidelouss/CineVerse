package com.cine.verse.Mapper;

import com.cine.verse.domain.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    Movie movieRequestToMovie(MovieRequest movieRequest);

    MovieResponse movieToMovieResponse(Movie movie);
}
