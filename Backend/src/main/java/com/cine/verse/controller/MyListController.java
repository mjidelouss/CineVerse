package com.cine.verse.controller;

import com.cine.verse.domain.MyList;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.MyListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class MyListController {

    private final MyListService myListService;

    @GetMapping("/all")
    public ResponseEntity getMyLists() {
        List<MyList> lists = myListService.getMyLists();
        if (lists.isEmpty()) {
            return ResponseMessage.notFound("Lists Not Found");
        } else {
            return ResponseMessage.ok("Success", lists);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getMyListById(@PathVariable Long id) {
        MyList list = myListService.getMyListById(id);
        if (list == null) {
            return ResponseMessage.notFound("List Not Found");
        } else {
            return ResponseMessage.ok("Success", list);
        }
    }

    @PostMapping("")
    public ResponseEntity addMyList(@RequestBody @Valid MyListRequest myListRequest) {
        MyList list = MyListMapper.mapMemberRequestToMember(myListRequest);
        MyList list1 = myListService.addMyList(list);
        if(list1 == null) {
            return ResponseMessage.badRequest("Failed To Create List");
        } else {
            return ResponseMessage.created("List Created Successfully", list1);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMyList(@RequestBody @Valid MyListRequest myListRequest, @PathVariable Long id) {
        MyList list = MyListMapper.mapMemberRequestToMember(myListRequest);
        MyList list1 = myListService.updateMyList(list, id);
        if (list1 == null) {
            return ResponseMessage.badRequest("List Not Updated");
        } else {
            return ResponseMessage.created("List Updated Successfully", list1);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteMyList(@PathVariable Long id) {
        MyList list = myListService.getMyListById(id);
        if (list == null) {
            return ResponseMessage.notFound("List Not Found");
        } else {
            myListService.deleteMyList(id);
            return ResponseMessage.ok("List Deleted Successfully", list);
        }
    }
}
