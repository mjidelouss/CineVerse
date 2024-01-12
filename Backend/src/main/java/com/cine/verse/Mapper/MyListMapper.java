package com.cine.verse.Mapper;

import com.cine.verse.Dto.request.MyListRequest;
import com.cine.verse.Dto.response.MyListResponse;
import com.cine.verse.domain.MyList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MyListMapper {
    MyList myListRequestToMyList(MyListRequest myListRequest);

    MyListResponse myListToMyListResponse(MyList myList);
}
