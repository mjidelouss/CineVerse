package com.cine.verse.service.impl;

import com.cine.verse.domain.MovieList;
import com.cine.verse.repository.MovieListRepository;
import com.cine.verse.service.MovieListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieListServiceImpl implements MovieListService {

    private final MovieListRepository movieListRepository;

    @Override
    public List<MovieList> getMovieLists() {
        return movieListRepository.findAll();
    }

    @Override
    public MovieList getMovieListById(Long id) {
        return movieListRepository.findById(id).orElse(null);
    }

    @Override
    public MovieList addMovieList(MovieList movieList) {
        return movieListRepository.save(movieList);
    }

    @Override
    public MovieList updateMovieList(MovieList movieList, Long id) {
        return null;
    }

    @Override
    public void deleteMovieList(Long id) {
        movieListRepository.deleteById(id);
    }
}
