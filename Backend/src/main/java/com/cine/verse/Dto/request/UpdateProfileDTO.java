package com.cine.verse.Dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileDTO {

    private String firstname;
    private String lastname;
    private String email;
    public MultipartFile image;
    private String bio;
    private String location;


}
