package com.example.auctionapp.repository;

import com.example.auctionapp.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT p FROM Product p
        WHERE (:name IS NULL OR :name = '' OR p.name LIKE %:name%)
        AND (:price IS NULL OR p.price >= :price)
        AND (:categoryId IS NULL OR p.category.id = :categoryId)
    """)
    Page<Product> searchProducts(
            @Param("name") String name,
            @Param("price") Double price,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );
}