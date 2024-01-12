package com.cine.verse.Mapper;

import com.cine.verse.domain.MovieList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieListMapper {
    MovieList movieListRequestToMovieList(MovieListRequest movieListRequest);

    MovieResponse movieListToMovieListResponse(MovieList movieList);
}
