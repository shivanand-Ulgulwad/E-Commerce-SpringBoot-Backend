package com.app.dto;

import lombok.Data;

@Data
public class PageRequestDto {
    private int page = 0;
    private int size = 5;
    private String sortBy = "id";
    private String sortDir = "asc";
}
