package com.cine.verse.service.impl;

import com.cine.verse.domain.List;
import com.cine.verse.repository.MovieListRepository;
import com.cine.verse.service.MovieListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieListServiceImpl implements MovieListService {

    private final MovieListRepository movieListRepository;

    @Override
    public java.util.List<List> getMovieLists() {
        return movieListRepository.findAll();
    }

    @Override
    public List getMovieListById(Long id) {
        return movieListRepository.findById(id).orElse(null);
    }

    @Override
    public List addMovieList(List movieList) {
        return movieListRepository.save(movieList);
    }

    @Override
    public List updateMovieList(List movieList, Long id) {
        return null;
    }

    @Override
    public void deleteMovieList(Long id) {
        movieListRepository.deleteById(id);
    }
}
