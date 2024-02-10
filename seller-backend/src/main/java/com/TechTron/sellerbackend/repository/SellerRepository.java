package com.TechTron.sellerbackend.repository;

import com.TechTron.sellerbackend.data.entity.Category;
import com.TechTron.sellerbackend.data.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {

}
