package com.cine.verse.service;

import com.cine.verse.domain.MyList;

import java.util.List;

public interface MyListService {
    List<MyList> getMyLists();

    MyList getMyListById(Long id);

    MyList addMyList(MyList list);

    MyList updateMyList(MyList list, Long id);

    void deleteMyList(Long id);
}
