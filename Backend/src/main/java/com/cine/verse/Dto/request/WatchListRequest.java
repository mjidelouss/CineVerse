package com.cine.verse.Dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchListRequest {
    Long userId;
    Long movieId;
}
