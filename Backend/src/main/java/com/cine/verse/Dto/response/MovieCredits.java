package com.cine.verse.Dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieCredits {
    private Long id;
    private List<String> cast;
    private String director;
    private String assistant_director;
    private String writer;
    private String costume_design;
    private String composer;
    private String cinematography;
    private String lighting;
    private List<String> set_decoration;
    private List<String> stunts;
    private List<String> visual_effects;
    private String add_director;
    private List<String> sound;
    private List<String> camera_operators;
    private String makeup;
    private List<String> hairstyling;
    private List<String> casting;
    private String editor;
    private List<String> producers;
    private List<String> exec_producers;
}
