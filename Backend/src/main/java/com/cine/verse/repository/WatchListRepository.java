package com.cine.verse.repository;

import com.cine.verse.domain.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList, Long> {
    WatchList findByMovieIdAndAppUserId(Long movieId, Long userId);
    List<WatchList> findByAppUserId(Long userId);
}
