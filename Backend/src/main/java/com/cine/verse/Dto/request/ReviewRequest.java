package com.cine.verse.Dto.request;

import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private Long userId;
    private Long movieId;
}
