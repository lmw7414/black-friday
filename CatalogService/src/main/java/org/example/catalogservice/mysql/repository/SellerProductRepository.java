package org.example.catalogservice.mysql.repository;

import org.example.catalogservice.mysql.entity.SellerProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerProductRepository extends JpaRepository<SellerProduct, Long> {
    List<SellerProduct> findBySellerId(Long sellerId);
}
