package com.product.management.repository;

import com.product.management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    
    // The findAll(Pageable) method is inherited. No need to add a new method signature here.
    
    @Query("SELECT p FROM Product p WHERE p.startDate <= CURRENT_DATE AND p.endDate >= CURRENT_DATE")
    List<Product> findActiveProducts();

}
