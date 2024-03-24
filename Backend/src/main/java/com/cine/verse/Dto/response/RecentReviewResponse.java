package com.cine.verse.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecentReviewResponse {
    private Long userId;
    private String userFirstname;
    private String userImage;
    private String userLastname;
    private Integer rating;
    private String content;
    private LocalDate timestamp;
}
