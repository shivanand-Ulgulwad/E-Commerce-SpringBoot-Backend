package com.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	 List<Product> findByCategoryId(Long categoryId);

	    // Search by name
	    List<Product> findByNameContaining(String name);

	    // Price filter
	    List<Product> findByPriceLessThan(double price);
    List<Product> findByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(String name, String categoryName);

    Page<Product> findByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCase(
            String name,
            String category,
            Pageable pageable
    );
	    boolean existsByCategoryId(Long categoryId);


}
