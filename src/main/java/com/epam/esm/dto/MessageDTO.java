package com.epam.esm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MessageDTO {
    private Integer status;
    private final String time = LocalDateTime.now().toString();
    private String message;
    private String url;
}
