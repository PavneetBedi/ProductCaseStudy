package com.amdocs.CaseStudy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.CaseStudy.java.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

}
