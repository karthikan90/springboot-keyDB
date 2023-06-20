package com.innova.demo.service;

import com.innova.demo.controller.ProductController;
import com.innova.demo.entity.Product;
import com.innova.demo.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Cacheable(value = "products", key = "#id")
    public Product getProductById(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        logger.info("Retrieving product with ID {} from the database", id);
        return productOptional.orElse(null);
    }

    @CacheEvict(value = "products", allEntries = true)
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @CacheEvict(value = "products", key = "#id")
    public Product update(int id, Product product) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            product.setId(id);
        }
        return productRepository.save(product);
    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }
}
