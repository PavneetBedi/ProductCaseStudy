package com.amdocs.CaseStudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.CaseStudy.exception.ProductNotFoundException;
import com.amdocs.CaseStudy.model.Product;
import com.amdocs.CaseStudy.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	private ProductService productservice;


	@Autowired
	public ProductController(ProductService productservice) {
		this.productservice = productservice;
	}
	
	@GetMapping("/allproducts")
	public List<Product> getAllProducts()
	{
		return productservice.getAllProducts();
	}
	
	@GetMapping("/{id}")
	public Product getproductbyId(@PathVariable Long id)
	{
		try {
			return productservice.getProductById(id);
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/insertproduct")
	public Product createProduct(@RequestBody Product product)
	{
		return productservice.createProduct(product);
	}
		
	@PutMapping("/{id}")
	public Product updateProductPrice(@PathVariable Long id, @RequestBody Product product)
	{
		return productservice.updateProductPrice(id, product);
	}
	
	@PutMapping("/name/{id}")
	public Product updateProductName(@PathVariable Long id, @RequestBody Product product)
	{
		return productservice.updateProductName(id, product);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCustById(@PathVariable Long id)
	{
	   productservice.deleteProductById(id);
	}

}
