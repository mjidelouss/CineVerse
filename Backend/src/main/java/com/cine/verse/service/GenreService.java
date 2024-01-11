package com.cine.verse.service;

import com.cine.verse.domain.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getGenres();

    Genre getGenreById(Long id);

    Genre addGenre(Genre genre);

    List<Genre> searchGenre(String searchTerm);

    Genre updateGenre(Genre genre, Long id);

    void deleteGenre(Long id);
}
