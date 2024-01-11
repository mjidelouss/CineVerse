package com.cine.verse.controller;

import com.cine.verse.domain.MovieList;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.MovieListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class MovieListController {

    private final MovieListService movieListService;

    @GetMapping("/all")
    public ResponseEntity getMovieLists() {
        List<MovieList> movieLists = movieListService.getMovieLists();
        if (movieLists.isEmpty()) {
            return ResponseMessage.notFound("Movie List Not Found");
        } else {
            return ResponseMessage.ok("Success", movieLists);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getMovieListById(@PathVariable Long id) {
        MovieList movieList = movieListService.getMovieListById(id);
        if (movieList == null) {
            return ResponseMessage.notFound("Movie List Not Found");
        } else {
            return ResponseMessage.ok("Success", movieList);
        }
    }

    @PostMapping("")
    public ResponseEntity addMovieList(@RequestBody @Valid MovieListRequest movieListRequest) {
        MovieList movieList = MovieListMapper.mapMemberRequestToMember(movieListRequest);
        MovieList movieList1 = movieListService.addMovieList(movieList);
        if(movieList1 == null) {
            return ResponseMessage.badRequest("Failed To Create Movie List");
        } else {
            return ResponseMessage.created("Movie List Created Successfully", movieList1);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMovieList(@RequestBody @Valid MovieListRequest movieListRequest, @PathVariable Long id) {
        MovieList movieList = MovieListMapper.mapMemberRequestToMember(movieListRequest);
        MovieList movieList1 = movieListService.updateMovieList(movieList, id);
        if (movieList1 == null) {
            return ResponseMessage.badRequest("Movie List Not Updated");
        } else {
            return ResponseMessage.created("Movie List Updated Successfully", movieList1);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteMovieList(@PathVariable Long id) {
        MovieList movieList = movieListService.getMovieListById(id);
        if (movieList == null) {
            return ResponseMessage.notFound("MovieList Not Found");
        } else {
            movieListService.deleteMovieList(id);
            return ResponseMessage.ok("Movie List Deleted Successfully", movieList);
        }
    }
}
