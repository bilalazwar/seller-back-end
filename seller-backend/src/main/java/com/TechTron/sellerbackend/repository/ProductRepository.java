package com.TechTron.sellerbackend.repository;

import com.TechTron.sellerbackend.data.entity.Category;
import com.TechTron.sellerbackend.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
}
