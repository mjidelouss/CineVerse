package com.cine.verse.controller;

import com.cine.verse.Dto.request.WatchListRequest;
import com.cine.verse.mappers.WatchListMapper;
import com.cine.verse.domain.WatchList;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.WatchListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/user/${id}")
    public ResponseEntity getWatchListByUser(@PathVariable Long id) {
        List<WatchList> watchLists = watchListService.getWatchListByUser(id);
        if (watchLists.isEmpty()) {
            return ResponseMessage.notFound("WatchList Not Found");
        } else {
            return ResponseMessage.ok("Success", watchLists);
        }
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
}
