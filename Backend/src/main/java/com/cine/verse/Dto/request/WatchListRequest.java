package com.cine.verse.Dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class WatchListRequest {
    Long userId;
    Long movieId;
}
