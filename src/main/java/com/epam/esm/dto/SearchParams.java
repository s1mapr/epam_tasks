package com.epam.esm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchParams {
    private String tagName;
    private String name;
    private String description;
    private boolean isSortByDate;
    private boolean isSortByName;
    private String sortType;
}
