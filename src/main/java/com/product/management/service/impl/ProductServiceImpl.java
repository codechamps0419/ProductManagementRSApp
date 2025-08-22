package com.product.management.service.impl;

import com.product.management.entity.CdProdTp;
import com.product.management.entity.Product;
import com.product.management.repository.CdProdTpRepository;
import com.product.management.repository.ProductRepository;
import com.product.management.service.ProductService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author : codechamps0419
 */

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
	private CdProdTpRepository cdProdTpRepository;

    @Override
    @Transactional
	public Product createProduct(Product product) {
		Optional<CdProdTp> cdProdTp = null;
		if (!cdProdTpRepository.existsByProductTypeId(product.getProductType().getProductTypeId())) {
			throw new IllegalArgumentException("Product type does not exist.");
		} else {
			cdProdTp = cdProdTpRepository.findById(product.getProductType().getProductTypeId());
		}
		product.setLastUpdatedDate(LocalDate.now());
		product.setProductType(cdProdTp.get());
		
		return productRepository.save(product);
	}

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product product) {
    	if (!cdProdTpRepository.existsByProductTypeId(product.getProductType().getProductTypeId())) {
            throw new IllegalArgumentException("Product type does not exist.");
        }
    	else {
			Optional<CdProdTp> cdProdTp = cdProdTpRepository.findById(product.getProductType().getProductTypeId());
			product.setProductType(cdProdTp.get());
		}
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setProductName(product.getProductName());
            existingProduct.setShortDesc(product.getShortDesc());
            existingProduct.setProductType(product.getProductType());
            existingProduct.setDescription(product.getDescription());
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
    }

    @Override
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> searchProducts(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }
    
    /**
     * Fetches a page of products.
     * @param pageable Contains pagination and sorting information.
     * @return A Page object containing products and pagination metadata.
     */
    @Override
    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

}