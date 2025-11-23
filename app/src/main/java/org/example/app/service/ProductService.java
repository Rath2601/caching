package org.example.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.app.cache.CacheConstants;
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
    
    @Cacheable(value = CacheConstants.CACHE_PRODUCTS, key = "{#root.methodName}")
    public List<Product> getAllProducts() throws InterruptedException {
        log.info("Fetching all products from database (cache miss)");
        Thread.sleep(3000);
        return productRepository.findAll();
    }
    
    @Cacheable(value = CacheConstants.CACHE_PRODUCT, key = "#id")
    public Optional<Product> getProductById(Long id) throws InterruptedException {
        log.info("Fetching product with id: {} from database (cache miss)", id);
        Thread.sleep(3000);
        return productRepository.findById(id);
    }
    
    @CacheEvict(value = {CacheConstants.CACHE_PRODUCTS, CacheConstants.CACHE_PRODUCT}, allEntries = true)
    public Product saveProduct(Product product) throws InterruptedException {
        log.info("Saving product to database: {}", product.getName());
        Thread.sleep(3000);
        return productRepository.save(product);
    }
}


