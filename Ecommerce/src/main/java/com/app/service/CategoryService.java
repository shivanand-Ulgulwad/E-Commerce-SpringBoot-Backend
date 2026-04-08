package com.app.service;

import java.util.List;

import com.app.dto.CategoryRequestDTO;
import com.app.dto.CategoryResponseDTO;
import com.app.dto.ProductRequestDTO;
import com.app.dto.ProductResponseDTO;
import com.app.entity.Category;

public interface CategoryService {
    CategoryResponseDTO create(CategoryRequestDTO dto);

    String createAllCategories(List<CategoryRequestDTO> list);

    List<CategoryResponseDTO> getAll();

    CategoryResponseDTO getById(Long id);

    void delete(Long id);

    void deleteAll();
}
