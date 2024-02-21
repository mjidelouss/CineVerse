package com.cine.verse.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TmdbApiResponse {

    private int page;
    private List<TmdbMovie> results;
    private int total_pages;
    private int total_results;
}
