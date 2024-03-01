package com.cine.verse.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TmdbCreditsResponse {
    private Long id;
    private List<Map<String, Object>> cast;
    private List<Map<String, Object>> crew;
}
