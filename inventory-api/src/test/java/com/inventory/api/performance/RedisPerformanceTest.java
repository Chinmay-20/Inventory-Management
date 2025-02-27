package com.inventory.api.performance;

import com.inventory.api.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RedisPerformanceTest {

    @Autowired
    private ProductService productService;

    @Test
    void testGetAllProductsPerformance() {
        long startWithoutCache = System.currentTimeMillis();
        productService.getAllProducts();  // First call (without cache)
        long endWithoutCache = System.currentTimeMillis();
        long durationWithoutCache = endWithoutCache - startWithoutCache;

        long startWithCache = System.currentTimeMillis();
        productService.getAllProducts();  // Second call (should hit cache)
        long endWithCache = System.currentTimeMillis();
        long durationWithCache = endWithCache - startWithCache;

        System.out.println("Without Redis Cache: " + durationWithoutCache + "ms");
        System.out.println("With Redis Cache: " + durationWithCache + "ms");

        assertTrue(durationWithCache < durationWithoutCache, "Redis should reduce response time");
    }
}
