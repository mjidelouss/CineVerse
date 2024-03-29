package com.cine.verse.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewResponse {
    private Boolean watched;
    private Boolean liked;
    private Boolean watchlist;
    private Integer rating;
}
