package com.cine.verse.mappers;

import com.cine.verse.Dto.request.MovieListRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieListMapper {
    List movieListRequestToMovieList(MovieListRequest movieListRequest);

    MovieListResponse movieListToMovieListResponse(List movieList);
}
