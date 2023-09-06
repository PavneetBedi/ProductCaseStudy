package com.amdocs.CaseStudy;

import com.amdocs.CaseStudy.controller.ProductController;
import com.amdocs.CaseStudy.exception.ProductNotFoundException;
import com.amdocs.CaseStudy.model.Product;
import com.amdocs.CaseStudy.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

//@WebMvcTest(ProductController.class) // Replace YourController with the actual name of your controller class
@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Mock
    private ProductService productService;
    
    @InjectMocks
    private ProductController productController;

    @Test
    public void testCreateProduct() throws Exception {
        // Create a Product object for the request body
        Product product = new Product();
        product.setPname("Test Product");
        product.setPid(5);
        product.setPrice(10);

        // Convert the Product object to JSON
        String productJson = objectMapper.writeValueAsString(product);

        // Perform a POST request to the endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/insertproduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isOk()) // Expect a 200 OK response
                .andExpect(jsonPath("$.pname").value("Test Product")) // Validate the response content
                .andExpect(jsonPath("$.pid").value(5))
                .andExpect(jsonPath("$.price").value(10));
    }
    
    @Test
    public void testDeleteProduct() throws Exception {
        // Define an existing product ID to delete
        long productIdToDelete = 1; // Replace with an existing product ID

        // Perform a DELETE request to delete the product
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/product/" + productIdToDelete))
                .andExpect(status().isOk()); // Expect a 200 OK response

        // You can add additional checks to verify that the product has been deleted from your system
    }

    @Test
    public void testGetAllProducts() {
        // Create a list of sample products
        List<Product> sampleProducts = new ArrayList<>();
        sampleProducts.add(new Product(1,10,"Product1"));
        sampleProducts.add(new Product(2,10,"Product2"));
        sampleProducts.add(new Product(3,10,"Product3"));

        // Mock the productService to return the sample products
        when(productService.getAllProducts()).thenReturn(sampleProducts);

        // Call the controller method
        List<Product> returnedProducts = productController.getAllProducts();

        // Verify that the returned list of products matches the sample products
        assertEquals(sampleProducts.size(), returnedProducts.size());
        for (int i = 0; i < sampleProducts.size(); i++) {
            assertEquals(sampleProducts.get(i).getPid(), returnedProducts.get(i).getPid());
            assertEquals(sampleProducts.get(i).getPname(), returnedProducts.get(i).getPname());
            assertEquals(sampleProducts.get(i).getPrice(), returnedProducts.get(i).getPrice(), 0.001); // Use delta for double comparison
        }
    }

    
    @Test
    public void testGetProductById() {
        // Create a sample product with a known ID
        Long productId = 1L;
        Product sampleProduct = new Product(productId, 10,"Product1");

        // Mock the productService to return the sample product when getById is called with the known ID
        try {
			when(productService.getProductById(productId)).thenReturn(sampleProduct);
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Call the controller method with the known ID
        Product returnedProduct = productController.getproductbyId(productId);

        // Verify that the returned product matches the sample product
        assertNotNull(returnedProduct);
        assertEquals(sampleProduct.getPid(), returnedProduct.getPid());
        assertEquals(sampleProduct.getPname(), returnedProduct.getPname());
        assertEquals(sampleProduct.getPrice(), returnedProduct.getPrice(), 0.001); // Use delta for double comparison
    }

    @Test
    public void testGetProductByIdNotFound() throws ProductNotFoundException {
        // Create a sample product with a known ID
        Long productId = 1L;

        // Mock the productService to return null when getById is called with the known ID, simulating a not found scenario
        when(productService.getProductById(productId)).thenReturn(null);

        // Call the controller method with the known ID
        Product returnedProduct = productController.getproductbyId(productId);

        // Verify that the returned product is null, indicating a not found scenario
        assertNull(returnedProduct);
    }
    
    @Test
    public void testUpdateProduct() {
        // Create a sample product ID
        Long productId = 1L;

        // Create a sample product object with updated data
        Product updatedProduct = new Product(productId, 15,"UpdatedProduct");

        // Mock the productService to return the updated product when updateProduct is called
        when(productService.updateProductName(eq(productId), any(Product.class))).thenReturn(updatedProduct);

        // Call the controller method with the sample product ID and updated product data
        Product returnedProduct = productController.updateProductName(productId, updatedProduct);

        // Verify that productService's updateProduct method was called with the same product ID and updated product
        verify(productService, times(1)).updateProductName(eq(productId), any(Product.class));

        // Verify that the returned product matches the updated product
        assertNotNull(returnedProduct);
        assertEquals(updatedProduct.getPname(), returnedProduct.getPname());
        assertEquals(updatedProduct.getPid(), returnedProduct.getPid());
        assertEquals(updatedProduct.getPrice(), returnedProduct.getPrice(), 0.001); // Use delta for double comparison
    }

}
