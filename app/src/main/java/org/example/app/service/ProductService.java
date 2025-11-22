package org.example.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.app.model.Product;
import org.example.app.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        log.info("Fetching all products from database");
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Long id) {
        log.info("Fetching product with id: {} from database", id);
        return productRepository.findById(id);
    }
    
    public Product saveProduct(Product product) {
        log.info("Saving product to database: {}", product.getName());
        return productRepository.save(product);
    }
}

