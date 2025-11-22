package org.example.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.app.model.Product;
import org.example.app.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    
    private final ProductService productService;
    
    /**
     * Get all products with simulated latency for testing caching
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() throws InterruptedException {
        log.info("Controller: Received request to get all products");
        
        // Simulate latency - sleep for 3 seconds to test caching benefits
        Thread.sleep(3000);
        log.info("Controller: Finished simulated latency (3 seconds)");
        
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    
    /**
     * Get product by ID with simulated latency for testing caching
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws InterruptedException {
        log.info("Controller: Received request to get product with id: {}", id);
        
        // Simulate latency - sleep for 3 seconds to test caching benefits
        Thread.sleep(3000);
        log.info("Controller: Finished simulated latency (3 seconds)");
        
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Create a new product
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        log.info("Controller: Creating new product: {}", product.getName());
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(savedProduct);
    }
}
