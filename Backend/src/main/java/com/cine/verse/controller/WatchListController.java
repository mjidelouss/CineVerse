package com.cine.verse.controller;

import com.cine.verse.Dto.request.WatchListRequest;
import com.cine.verse.Dto.response.UserWatchListResponse;
import com.cine.verse.domain.Movie;
import com.cine.verse.domain.WatchList;
import com.cine.verse.mappers.WatchListMapper;
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

    @GetMapping("/user/{id}")
    public ResponseEntity getWatchListByUser(@PathVariable Long id) {
        List<WatchList> watchLists = watchListService.getWatchListByUser(id);
        if (watchLists.isEmpty()) {
            return ResponseMessage.notFound("WatchList Not Found");
        } else {
            List<UserWatchListResponse> userWatchListResponses = watchLists.stream()
                    .map(WatchListMapper::convertWatchListToUserWatchList).collect(Collectors.toList());
            return ResponseMessage.ok("Success", userWatchListResponses);
        }
    }

    @GetMapping("/filterWatchedMoviesByGenre/{userId}")
    public ResponseEntity getWatchedMoviesByGenre(@RequestParam(name = "genre", required = false) String genreName, @PathVariable Long userId) {
        List<WatchList> allWacthList = watchListService.getWatchListByUser(userId);
        List<UserWatchListResponse> userWatchListResponses = allWacthList.stream().map(WatchListMapper::convertWatchListToUserWatchList)
                .collect(Collectors.toList());
        if (genreName == null || genreName.isEmpty()) {
            return ResponseMessage.ok("No Genre Specified", userWatchListResponses);
        }
        List<WatchList> filteredWatchList = allWacthList.stream()
                .filter(watchList -> {
                    return watchList.getMovie().getGenres().stream()
                            .anyMatch(genre -> genre.getName().equalsIgnoreCase(genreName));
                }).collect(Collectors.toList());
        if (filteredWatchList.isEmpty()) {
            return ResponseMessage.notFound("Filtred WatchList Not Found");
        } else {
            List<UserWatchListResponse> result = filteredWatchList.stream().map(WatchListMapper::convertWatchListToUserWatchList)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Success", result);
        }
    }

    @GetMapping("/filterWatchedMoviesByDecade/{userId}")
    public ResponseEntity getWatchedMoviesByDecade(@RequestParam("decade") String decade, @PathVariable Long userId) {
        List<WatchList> allWatchList = watchListService.getWatchListByUser(userId);
        List<UserWatchListResponse> userWatchListResponses = allWatchList.stream().map(WatchListMapper::convertWatchListToUserWatchList)
                .collect(Collectors.toList());
        if (decade == null || decade.isEmpty()) {
            return ResponseMessage.ok("Decade was not Specified", userWatchListResponses);
        }
        List<WatchList> filteredWatchList = allWatchList.stream()
                .filter(watchList -> isMovieInDecade(watchList.getMovie(), decade))
                .collect(Collectors.toList());
        if (filteredWatchList.isEmpty()) {
            return ResponseMessage.notFound("Filtred WatchList Not Found");
        } else {
            List<UserWatchListResponse> result = filteredWatchList.stream().map(WatchListMapper::convertWatchListToUserWatchList)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Success", result);
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
