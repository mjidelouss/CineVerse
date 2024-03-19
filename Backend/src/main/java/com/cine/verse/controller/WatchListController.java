package com.cine.verse.controller;

import com.cine.verse.Dto.request.WatchListRequest;
import com.cine.verse.domain.Movie;
import com.cine.verse.domain.Review;
import com.cine.verse.mappers.WatchListMapper;
import com.cine.verse.domain.WatchList;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.WatchListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/watchlist")
@RequiredArgsConstructor
public class WatchListController {

    private final WatchListService watchListService;
    private final WatchListMapper watchListMapper;

    @GetMapping("/all")
    public ResponseEntity getWatchLists() {
        List<WatchList> watchLists = watchListService.getWatchLists();
        if (watchLists.isEmpty()) {
            return ResponseMessage.notFound("WatchLists Not Found");
        } else {
            return ResponseMessage.ok("Success", watchLists);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getWatchListByUser(@PathVariable Long id) {
        List<WatchList> watchLists = watchListService.getWatchListByUser(id);
        if (watchLists.isEmpty()) {
            return ResponseMessage.notFound("WatchList Not Found");
        } else {
            return ResponseMessage.ok("Success", watchLists);
        }
    }

    @GetMapping("/filterWatchedMoviesByGenre/{userId}")
    public List<WatchList> getWatchedMoviesByGenre(@RequestParam(name = "genre", required = false) String genreName, @PathVariable Long userId) {
        // Retrieve movies by genre from the service layer
        List<WatchList> allWacthList = watchListService.getWatchListByUser(userId);
        // Check if genreName is empty or null
        if (genreName == null || genreName.isEmpty()) {
            return allWacthList;
        }
        // Filter movies by the specified genre
        return allWacthList.stream()
                .filter(watchList -> {
                    return watchList.getMovie().getGenres().stream()
                            .anyMatch(genre -> genre.getName().equalsIgnoreCase(genreName));
                })
                .collect(Collectors.toList()); // Collect the filtered movies into a list
    }

    @GetMapping("/filterWatchedMoviesByDecade/{userId}")
    public List<WatchList> getWatchedMoviesByDecade(@RequestParam("decade") String decade, @PathVariable Long userId) {
        // Retrieve movies from the service layer
        List<WatchList> allWatchList = watchListService.getWatchListByUser(userId);

        if (decade == null || decade.isEmpty()) {
            return allWatchList;
        }
        // Filter movies by the specified decade
        return allWatchList.stream()
                .filter(watchList -> isMovieInDecade(watchList.getMovie(), decade))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity getWatchListById(@PathVariable Long id) {
        WatchList watchList = watchListService.getWatchListById(id);
        if (watchList == null) {
            return ResponseMessage.notFound("WatchList Not Found");
        } else {
            return ResponseMessage.ok("Success", watchList);
        }
    }

    @PostMapping("")
    public ResponseEntity addWatchList(@RequestBody @Valid WatchListRequest watchListRequest) {
        WatchList watchList1 = watchListService.addWatchList(watchListRequest.getUserId(), watchListRequest.getMovieId());
        if(watchList1 == null) {
            return ResponseMessage.badRequest("Failed To Create WatchList");
        } else {
            return ResponseMessage.created("WatchList Created Successfully", watchList1);
        }
    }

    @PostMapping("/remove")
    public ResponseEntity removeMovieFromWatchList(@RequestBody @Valid WatchListRequest watchListRequest) {
        WatchList watchList = watchListService.getWatchListByMovieIdAndUserId(watchListRequest.getMovieId(), watchListRequest.getUserId());
        if (watchList == null) {
            return ResponseMessage.notFound("WatchList Not Found");
        } else {
            watchListService.deleteWatchList(watchList.getId());
            return ResponseMessage.ok("WatchList Deleted Successfully", watchList);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateWatchList(@RequestBody @Valid WatchListRequest watchListRequest, @PathVariable Long id) {
        WatchList watchList = watchListMapper.watchListRequestToWatchList(watchListRequest);
        WatchList watchList1 = watchListService.updateWatchList(watchList, id);
        if (watchList1 == null) {
            return ResponseMessage.badRequest("WatchList Not Updated");
        } else {
            return ResponseMessage.created("WatchList Updated Successfully", watchList1);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteWatchList(@PathVariable Long id) {
        WatchList watchList = watchListService.getWatchListById(id);
        if (watchList == null) {
            return ResponseMessage.notFound("WatchList Not Found");
        } else {
            watchListService.deleteWatchList(id);
            return ResponseMessage.ok("WatchList Deleted Successfully", watchList);
        }
    }

    private boolean isMovieInDecade(Movie movie, String decade) {
        int year = movie.getYear();
        switch (decade.toLowerCase()) {
            case "1860s":
                return year >= 1860 && year <= 1869;
            case "1870s":
                return year >= 1870 && year <= 1879;
            case "1880s":
                return year >= 1880 && year <= 1889;
            case "1890s":
                return year >= 1890 && year <= 1899;
            case "1900s":
                return year >= 1900 && year <= 1909;
            case "1910s":
                return year >= 1910 && year <= 1919;
            case "1920s":
                return year >= 1920 && year <= 1929;
            case "1930s":
                return year >= 1930 && year <= 1939;
            case "1940s":
                return year >= 1940 && year <= 1949;
            case "1950s":
                return year >= 1950 && year <= 1959;
            case "1960s":
                return year >= 1960 && year <= 1969;
            case "1970s":
                return year >= 1970 && year <= 1979;
            case "1980s":
                return year >= 1980 && year <= 1989;
            case "1990s":
                return year >= 1990 && year <= 1999;
            case "2000s":
                return year >= 2000 && year <= 2009;
            case "2010s":
                return year >= 2010 && year <= 2019;
            case "2020s":
                return year >= 2020 && year <= 2024; // Up to 2024
            default:
                return false;
        }
    }
}
