package com.product.management.controller;

import com.product.management.entity.Product;
import com.product.management.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Product createProduct(@RequestBody Product product) {
        product.setLastUpdatedDate(LocalDate.now());
        return productRepository.save(product);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product productDetails) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setProductName(productDetails.getProductName());
                    existingProduct.setShortDesc(productDetails.getShortDesc());
                    existingProduct.setDescription(productDetails.getDescription());
                    existingProduct.setStartDate(productDetails.getStartDate());
                    existingProduct.setEndDate(productDetails.getEndDate());
                    existingProduct.setLastUpdatedUser(productDetails.getLastUpdatedUser());
                    existingProduct.setLastUpdatedDate(LocalDate.now());
                    existingProduct.setProductType(productDetails.getProductType());
                    Product updatedProduct = productRepository.save(existingProduct);
                    return ResponseEntity.ok(updatedProduct);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Product> searchProducts(@RequestParam("name") String name) {
        return productRepository.findByProductNameContainingIgnoreCase(name);
    }
}
