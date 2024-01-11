package com.cine.verse.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    public int status;
    public String message;
    public Object data;

    public ResponseMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Ok Response
    public static ResponseEntity<ResponseMessage>  ok(String message, Object data) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), message, data), HttpStatus.OK);
    }

    // Created Response
    public static ResponseEntity<ResponseMessage>  created(String message, Object data) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.CREATED.value(), message, data), HttpStatus.CREATED);
    }

    // Not Found Response
    public static ResponseEntity<ResponseMessage>  notFound(String message) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.NOT_FOUND.value(), message), HttpStatus.NOT_FOUND);
    }

    // Bad Request
    public static ResponseEntity<ResponseMessage>  badRequest(String message) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), message), HttpStatus.BAD_REQUEST);
    }
}