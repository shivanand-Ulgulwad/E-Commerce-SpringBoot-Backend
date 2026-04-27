package com.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;


import com.app.dto.CategoryRequestDTO;
import com.app.dto.CategoryResponseDTO;
import com.app.entity.Category;
import com.app.entity.Product;
import com.app.mapper.CategoryMapper;
import com.app.mapper.ProductMapper;
import com.app.repository.CategoryRepository;
import com.app.repository.ProductRepository;
import com.app.service.CategoryService;

@Service("CategoryService")
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final ModelMapper mapper;

	    private final CategoryRepository categoryRepo;


	    private final ProductRepository productRepo;

	    // ✅ CREATE CATEGORY
	    @Override
	    public CategoryResponseDTO create(CategoryRequestDTO dto) {

	        Category category = CategoryMapper.toEntity(dto);

	        Category saved = categoryRepo.save(category);

	        return CategoryMapper.toDTO(saved);
	    }

    @Override
    public String createAllCategories(List<CategoryRequestDTO> list) {
            List<Category> list1=list.
                    stream()
                    .map(x -> mapper.map(x,Category.class)).toList();
            categoryRepo.saveAll(list1);
        return "All Categories are saved";
    }

    // ✅ GET ALL
	    @Override
	    public List<CategoryResponseDTO> getAll() {

	        return categoryRepo.findAll()
	                .stream()
	                .map(CategoryMapper::toDTO)
	                .toList();
	    }

	    // ✅ GET BY ID
	    @Override
	    public CategoryResponseDTO getById(Long id) {

	        Category category = categoryRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Category not found"));

	        return CategoryMapper.toDTO(category);
	    }

	    // 🔥 DELETE CATEGORY (IMPORTANT LOGIC)
	    @Override
	    public void delete(Long id) {

	        Category category = categoryRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Category not found"));

	        // 🔴 REAL WORLD CHECK
	        
	        boolean hasProducts = productRepo.existsByCategoryId(id);

	        if (hasProducts) {
	            throw new RuntimeException("Cannot delete category. Products exist.");
	        }

	        categoryRepo.delete(category);
	    }

    @Override
    public void deleteAll() {
        categoryRepo.deleteAll();
    }

}
