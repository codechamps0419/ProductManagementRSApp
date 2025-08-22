package com.product.management.util;

import com.product.management.entity.CdProdTp;
import com.product.management.entity.Product;
import com.product.management.repository.CdProdTpRepository;
import com.product.management.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

/**
 * @author : codechamps0419
 */

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
            CdProdTp cdProdTp1 = new CdProdTp();
            cdProdTp1.setProductTypeName("Test Product");
            cdProdTp1.setProductDescription("Test Product");
            CdProdTpRepository.save(cdProdTp1);

            CdProdTp cdProdTp2 = new CdProdTp();
            cdProdTp2.setProductTypeName("Health & Wellness");
            cdProdTp2.setProductDescription("Health & Wellness");
            CdProdTpRepository.save(cdProdTp2);
            
            CdProdTp cdProdTp3 = new CdProdTp();
            cdProdTp3.setProductTypeName("Investment Product");
            cdProdTp3.setProductDescription("Investment Product");
            CdProdTpRepository.save(cdProdTp3);
        }

        if (productRepository.count() == 0) {
            CdProdTp cdProdTp1 = CdProdTpRepository.findById(1L).orElseThrow();
            CdProdTp cdProdTp2 = CdProdTpRepository.findById(2L).orElseThrow();

            Product product1 = new Product();
            product1.setProductName("Test Product");
            product1.setShortDesc("Test Product");
            product1.setDescription("This is a test product");
            product1.setStartDate(LocalDate.now());
            product1.setEndDate(LocalDate.now().plusYears(1));
            product1.setLastUpdatedUser("admin");
            product1.setLastUpdatedDate(LocalDate.now());
            product1.setProductType(cdProdTp1);
            productRepository.save(product1);

            Product product2 = new Product();
            product2.setProductName("Elderly Care Package");
            product2.setShortDesc("A comprehensive package for senior citizens.");
            product2.setDescription("Includes at-home medical check-ups, assisted living consultation, "
            		+ "and a weekly meal delivery service tailored for seniors.");
            product2.setStartDate(LocalDate.now().minusMonths(2));
            product2.setEndDate(LocalDate.now().plusMonths(10));
            product2.setLastUpdatedUser("admin");
            product2.setLastUpdatedDate(LocalDate.now());
            product2.setProductType(cdProdTp2);
            productRepository.save(product2);
        }
    }
}
