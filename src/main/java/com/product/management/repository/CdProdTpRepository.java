package com.product.management.repository;

import com.product.management.entity.CdProdTp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdProdTpRepository extends JpaRepository<CdProdTp, Long> {
	
}
