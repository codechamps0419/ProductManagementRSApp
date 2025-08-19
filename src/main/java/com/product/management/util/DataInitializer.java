package com.product.management.util;

import com.product.management.entity.CdProdTp;
import com.product.management.entity.Product;
import com.product.management.repository.CdProdTpRepository;
import com.product.management.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final CdProdTpRepository CdProdTpRepository;

    public DataInitializer(ProductRepository productRepository, CdProdTpRepository CdProdTpRepository) {
        this.productRepository = productRepository;
        this.CdProdTpRepository = CdProdTpRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (CdProdTpRepository.count() == 0) {
            CdProdTp electronicsType = new CdProdTp();
            electronicsType.setProductTypeName("Electronics");
            electronicsType.setProductDescription("Electronic gadgets and devices");
            CdProdTpRepository.save(electronicsType);

            CdProdTp clothingType = new CdProdTp();
            clothingType.setProductTypeName("Clothing");
            clothingType.setProductDescription("Apparel and fashion items");
            CdProdTpRepository.save(clothingType);
        }

        if (productRepository.count() == 0) {
            CdProdTp electronicsType = CdProdTpRepository.findById(1L).orElseThrow();
            CdProdTp clothingType = CdProdTpRepository.findById(2L).orElseThrow();

            Product product1 = new Product();
            product1.setProductName("Laptop");
            product1.setShortDesc("Powerful and portable computer");
            product1.setDescription("A high-performance laptop with 16GB RAM and 512GB SSD.");
            product1.setStartDate(LocalDate.now());
            product1.setEndDate(LocalDate.now().plusYears(1));
            product1.setLastUpdatedUser("admin");
            product1.setLastUpdatedDate(LocalDate.now());
            product1.setProductType(electronicsType);
            productRepository.save(product1);

            Product product2 = new Product();
            product2.setProductName("T-shirt");
            product2.setShortDesc("Comfortable cotton t-shirt");
            product2.setDescription("A 100% cotton t-shirt, available in multiple sizes and colors.");
            product2.setStartDate(LocalDate.now().minusMonths(2));
            product2.setEndDate(LocalDate.now().plusMonths(10));
            product2.setLastUpdatedUser("user1");
            product2.setLastUpdatedDate(LocalDate.now());
            product2.setProductType(clothingType);
            productRepository.save(product2);
        }
    }
}
