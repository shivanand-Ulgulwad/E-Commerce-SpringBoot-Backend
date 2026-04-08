package com.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDTO {

    private Long id;
    @NotBlank(message = "Category name is required")
    private String name;
}
