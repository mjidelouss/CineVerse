package com.cine.verse.service.impl;

import com.cine.verse.domain.Genre;
import com.cine.verse.repository.GenreRepository;
import com.cine.verse.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getGenreById(Long id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public List<Genre> searchGenre(String searchTerm) {
        return null;
    }

    @Override
    public Genre updateGenre(Genre genre, Long id) {
        return null;
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}
