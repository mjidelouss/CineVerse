package com.cine.verse.mappers;

import com.cine.verse.Dto.request.GenreRequest;
import com.cine.verse.Dto.response.GenreResponse;
import com.cine.verse.domain.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    Genre genreRequestToGenre(GenreRequest genreRequest);

    GenreResponse genreToGenreResponse(Genre genre);
}
