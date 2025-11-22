package org.example.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.app.model.Product;
import org.example.app.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    
    private final ProductRepository productRepository;
    
   @Cacheable("products")
    public List<Product> getAllProducts() throws InterruptedException {
        log.info("Fetching all products from database");
        Thread.sleep(3000);
        return productRepository.findAll();
    }
    
    @Cacheable(value = "product", key = "#id")
    public Optional<Product> getProductById(Long id) throws InterruptedException {
        log.info("Fetching product with id: {} from database", id);
        Thread.sleep(3000);
        return productRepository.findById(id);
    }
    
     @CacheEvict(value = {"products", "product"}, allEntries = true)
    public Product saveProduct(Product product) throws InterruptedException {
        log.info("Saving product to database: {}", product.getName());
        Thread.sleep(3000);
        return productRepository.save(product);
    }
}

