package com.cine.verse.repository;

import com.cine.verse.domain.MyList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyListRepository extends JpaRepository<MyList, Long> {

}
