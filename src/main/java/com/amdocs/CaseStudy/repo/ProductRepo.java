package com.amdocs.CaseStudy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.CaseStudy.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

}
