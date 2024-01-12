package com.cine.verse.Mapper;

import com.cine.verse.domain.MyList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MyListMapper {
    MyList myListRequestToMyList(MyListRequest myListRequest);

    MyListResponse myListToMyListResponse(MyList myList);
}
