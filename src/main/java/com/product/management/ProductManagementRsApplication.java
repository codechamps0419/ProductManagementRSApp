package com.product.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * @author : codechamps0419
 */

@SpringBootApplication
@OpenAPIDefinition (info =  @Info (title = "PRODUCT MANAGEMENT RS APP", version = "1.0.0",description = "PMRS"))
public class ProductManagementRsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductManagementRsApplication.class, args);
	}

}
