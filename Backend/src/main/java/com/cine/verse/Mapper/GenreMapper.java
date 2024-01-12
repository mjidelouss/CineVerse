package com.cine.verse.Mapper;

import com.cine.verse.domain.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    Genre genreRequestToGenre(GenreRequest genreRequest);

    GenreResponse genreToGenreResponse(Genre genre);
}
