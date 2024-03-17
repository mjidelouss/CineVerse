package com.cine.verse.service;

import com.cine.verse.domain.List;

public interface MovieListService {
    java.util.List<List> getMovieLists();

    List getMovieListById(Long id);

    List addMovieList(List movieList);

    List updateMovieList(List movieList, Long id);

    void deleteMovieList(Long id);
}
