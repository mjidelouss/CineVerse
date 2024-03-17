package com.cine.verse.repository;

import com.cine.verse.domain.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieListRepository extends JpaRepository<List, Long> {
}
