package com.cine.verse.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    MovieCredits movieCredits;
    MovieDetailsTrailer movieDetailsTrailer;
}
