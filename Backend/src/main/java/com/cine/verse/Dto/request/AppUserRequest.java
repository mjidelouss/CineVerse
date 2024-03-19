package com.cine.verse.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserRequest {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    private String image;
    private String bio;
    private String location;
}
