package com.cine.verse.service.impl;

import com.cine.verse.domain.MyList;
import com.cine.verse.repository.MyListRepository;
import com.cine.verse.service.MyListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyListServiceImpl implements MyListService {

    private final MyListRepository myListRepository;

    @Override
    public List<MyList> getMyLists() {
        return myListRepository.findAll();
    }

    @Override
    public MyList getMyListById(Long id) {
        return myListRepository.findById(id).orElse(null);
    }

    @Override
    public MyList addMyList(MyList list) {
        return myListRepository.save(list);
    }

    @Override
    public MyList updateMyList(MyList list, Long id) {
        return null;
    }

    @Override
    public void deleteMyList(Long id) {
        myListRepository.deleteById(id);
    }
}
