package com.amdocs.CaseStudy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amdocs.CaseStudy.exception.ProductNotFoundException;
import com.amdocs.CaseStudy.model.Product;
import com.amdocs.CaseStudy.repo.ProductRepo;


@Service
public class ProductService {
	
		private ProductRepo productrepo;
			
		
			@Autowired
			public ProductService(ProductRepo productrepo) {
				this.productrepo = productrepo;
			}
			
			public List<Product> getAllProducts()
			{
				
				return productrepo.findAll();
			}
			
			public Product getProductById(Long id) throws ProductNotFoundException
			{
		         Product p= productrepo.findById(id).orElse(null);
		         
		         if(p==null)
		        
		        	 throw new ProductNotFoundException(" Product not exist for given id : "+id);
		       	 
		         return p;
		       	 
			}
			
			public Product createProduct(Product p)
			{
				return productrepo.save(p);
			}
			
			
			public Product updateProductName(Long id, Product p)
			{
				
				Product existingproduct=productrepo.findById(id).orElse(null);
				if(existingproduct!=null)
				{
					existingproduct.setPname(p.getPname());
					//existingproduct.setPrice(p.getPrice());
				}
				return productrepo.save(existingproduct);
			}
			
			public Product updateProductPrice(Long id, Product p)
			{
				
				Product existingproduct=productrepo.findById(id).orElse(null);
				if(existingproduct!=null)
				{
					existingproduct.setPrice(p.getPrice());
				}
				return productrepo.save(existingproduct);
			}
			
			public void deleteProductById(Long id)
			{
				
				productrepo.deleteById(id);
				
			}

}
