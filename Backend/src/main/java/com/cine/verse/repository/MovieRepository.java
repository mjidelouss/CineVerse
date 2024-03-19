package com.cine.verse.repository;

import com.cine.verse.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitleAndYear(String title, Integer year);

    List<Movie> findTop6ByOrderByIdDesc();

    List<Movie> findByTitleContainingIgnoreCase(String searchTerm);
}
