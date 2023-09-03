package com.epam.esm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SearchParams {
    private List<TagDTO> tagName;
    private String name;
    private String description;
    private boolean isSortByDate;
    private boolean isSortByName;
    private String sortType;
    private Integer page;
}
