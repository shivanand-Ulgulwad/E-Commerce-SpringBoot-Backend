package com.app.service;

import java.util.List;

import org.springframework.data.domain.Page;


import com.app.dto.ProductRequestDTO;
import com.app.dto.ProductResponseDTO;
import com.app.entity.Product;

public interface ProductService {

    ProductResponseDTO create(ProductRequestDTO dto);
    String createAll(List<ProductRequestDTO> list);

    List<ProductResponseDTO> getAll();

    ProductResponseDTO getById(Long id);

    public List<ProductResponseDTO> searchProducts(String query);

    void delete(Long id);

    void deleteAll();
}