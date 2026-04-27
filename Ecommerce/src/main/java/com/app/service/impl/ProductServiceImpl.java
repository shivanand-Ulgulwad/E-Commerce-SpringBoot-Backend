package com.app.service.impl;

import java.util.List;

import com.app.dto.PageRequestDto;
import com.app.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.app.dto.ProductRequestDTO;
import com.app.dto.ProductResponseDTO;
import com.app.entity.Category;
import com.app.entity.Product;
import com.app.mapper.ProductMapper;
import com.app.repository.CategoryRepository;
import com.app.repository.ProductRepository;
import com.app.service.ProductService;

@Service("productService")
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ModelMapper mapper;

	    private final ProductRepository productRepo;


	    private final CategoryRepository categoryRepo;

	    // ✅ CREATE PRODUCT
	    @Override
	    public ProductResponseDTO create(ProductRequestDTO dto) {

	        // 🔴 VALIDATION
	        if (dto.getCategoryId() == null) {
	            throw new RuntimeException("CategoryId is required");
	        }

	        // 🔴 FETCH CATEGORY
	        Category category = categoryRepo.findById(dto.getCategoryId())
	                .orElseThrow(() -> new RuntimeException("Category not found"));

	        // 🔴 CONVERT DTO → ENTITY
	        Product product = ProductMapper.toEntity(dto, category);

	        // 🔴 SAVE
	        Product saved = productRepo.save(product);

	        // 🔴 RETURN DTO
	        return ProductMapper.toDTO(saved);
	    }

    @Override
    public String createAll(List<ProductRequestDTO> list) {

            List<Product> list1 = list.stream()

                    .map(x -> {
                        Category category = categoryRepo.findById(x.getCategoryId())
                                .orElseThrow(() -> new RuntimeException("Category not found: " + x.getCategoryId()));

                        return Product.builder()
                                .name(x.getName())
                                .price(x.getPrice())
                                .category(category) // ✅ FIX HERE
                                .build();
                    })
                    .toList();

            List<Product> list2 =productRepo.saveAll(list1);

        return "All Products are Saved";
    }

    // ✅ GET ALL PRODUCTS
	    @Override
	    public List<ProductResponseDTO> getAll() {

	        return productRepo.findAll()
	                .stream()
	                .map(ProductMapper::toDTO)
	                .toList();
	    }

	    // ✅ GET BY ID
	    @Override
	    public ProductResponseDTO getById(Long id) {

	        Product product = productRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Product not found"));

	        return ProductMapper.toDTO(product);
	    }

	    // ✅ DELETE PRODUCT
	    @Override
	    public void delete(Long id) {

	        if (!productRepo.existsById(id)) {
	            throw new RuntimeException("Product not found");
	        }

	        productRepo.deleteById(id);
	    }

    @Override
    public void deleteAll() {
        productRepo.deleteAll();
    }

    public List<ProductResponseDTO> searchProducts(String query) {
        List<ProductResponseDTO> products =
                productRepo.findByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(query, query)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
        return products;
    }


    @Override
    public PageResponseDto<ProductResponseDTO> getProducts(
            String query,
            PageRequestDto pageRequestDto) {

        // 1. Sorting
        Sort sort = pageRequestDto.getSortDir().equalsIgnoreCase("asc") ?
                Sort.by(pageRequestDto.getSortBy()).ascending() :
                Sort.by(pageRequestDto.getSortBy()).descending();

        // 2. Pageable
        Pageable pageable = PageRequest.of(
                pageRequestDto.getPage(),
                pageRequestDto.getSize(),
                sort
        );

        // 3. Fetch Data
        Page<Product> productPage;

        if (query != null && !query.trim().isEmpty()) {
            query = query.trim();
            productPage = productRepo
                    .findByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCase(query,query, pageable);
        } else {
            productPage = productRepo.findAll(pageable);
        }

        // 4. Convert to DTO
        List<ProductResponseDTO> content = productPage
                .getContent()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();

        // 5. Build Response
        return new PageResponseDto<>(
                content,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast()
        );
    }


}
