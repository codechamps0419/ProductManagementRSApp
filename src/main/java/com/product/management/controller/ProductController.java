package com.product.management.controller;

import com.product.management.entity.Product;
import com.product.management.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : codechamps0419
 */

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		try {
			Product createdProduct = productService.createProduct(product);
			return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Product>> getAllProducts() {
		try {
			List<Product> products = productService.getAllProducts();
			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
		return productService.getProductById(id).map(product -> new ResponseEntity<>(product, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
		try {
			Product updatedProduct = productService.updateProduct(id, product);
			return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
		boolean isDeleted = productService.deleteProduct(id);
		if (isDeleted) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/search", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Product>> searchProducts(@RequestParam("name") String name) {
		List<Product> products = productService.searchProducts(name);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	/**
	 * Endpoint to get a paginated and sortable list of products. Example: GET
	 * /products/paginated?page=0&size=10&sort=productName,asc
	 * 
	 * @param pageable Spring automatically creates this from request parameters.
	 * @return A Page of products with pagination metadata.
	 */
	@GetMapping("/paginated")
	public ResponseEntity<Page<Product>> getProductsPaginated(
			@PageableDefault(sort = "productName") Pageable pageable) {
		Page<Product> productPage = productService.getProducts(pageable);
		return ResponseEntity.ok(productPage);
	}
}