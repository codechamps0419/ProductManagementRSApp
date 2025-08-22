package com.product.management.service;

import com.product.management.entity.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
	Product createProduct(Product product);

	Product updateProduct(Long id, Product product);

	boolean deleteProduct(Long id);

	Optional<Product> getProductById(Long id);

	List<Product> getAllProducts();

	List<Product> searchProducts(String name);

	Page<Product> getProducts(Pageable pageable);
}