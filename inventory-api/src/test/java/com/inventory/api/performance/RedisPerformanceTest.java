// // package com.inventory.api.performance;

// // import com.inventory.api.service.ProductService;
// // import org.junit.jupiter.api.Test;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.boot.test.context.SpringBootTest;

// // import static org.junit.jupiter.api.Assertions.assertTrue;

// // @SpringBootTest
// // public class RedisPerformanceTest {

// //     @Autowired
// //     private ProductService productService;

// //     @Test
// //     void testGetAllProductsPerformance() {
// //         long startWithoutCache = System.currentTimeMillis();
// //         productService.getAllProducts(false);  // First call (without cache)
// //         long endWithoutCache = System.currentTimeMillis();
// //         long durationWithoutCache = endWithoutCache - startWithoutCache;

// //         long startWithCache = System.currentTimeMillis();
// //         productService.getAllProducts(true);  // Second call (should hit cache)
// //         long endWithCache = System.currentTimeMillis();
// //         long durationWithCache = endWithCache - startWithCache;

// //         System.out.println("Without Redis Cache: " + durationWithoutCache + "ms"); // first time it should bring from database execution time 272 ms
// //         System.out.println("With Redis Cache: " + durationWithCache + "ms"); // 2nd time it should bring from cache execution time 17ms

// //         assertTrue(durationWithCache < durationWithoutCache, "Redis should reduce response time");
// //     }
// // }

// package com.inventory.api.performance;

// import com.inventory.api.dto.ProductDTO;
// import com.inventory.api.service.ProductService;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import static org.junit.jupiter.api.Assertions.assertTrue;
// import java.util.List;
// @SpringBootTest
// public class RedisPerformanceTest {

//     @Autowired
//     private ProductService productService;

//     // @Test
//     // void testGetAllProductsPerformance() {
//     //     long startWithoutCache = System.currentTimeMillis();
//     //     productService.getAllProducts(false);
//     //     long endWithoutCache = System.currentTimeMillis();
//     //     long durationWithoutCache = endWithoutCache - startWithoutCache;

//     //     long startWithCache = System.currentTimeMillis();
//     //     productService.getAllProducts(true);
//     //     long endWithCache = System.currentTimeMillis();
//     //     long durationWithCache = endWithCache - startWithCache;

//     //     System.out.println("Without Redis Cache: " + durationWithoutCache + "ms");
//     //     System.out.println("With Redis Cache: " + durationWithCache + "ms");

//     //     assertTrue(durationWithCache < durationWithoutCache, "Redis should reduce response time");
//     // }

//     @Test
//         void testGetAllProductsPerformance() {
//     long startTime = System.nanoTime();
//     List<ProductDTO> firstCall = productService.getAllProducts(false);
//     long firstDuration = System.nanoTime() - startTime;

//     // Call again to check if Redis is used
//     startTime = System.nanoTime();
//     List<ProductDTO> cachedCall = productService.getAllProducts(false);
//     long cachedDuration = System.nanoTime() - startTime;

//     System.out.println("First call duration: " + firstDuration);
//     System.out.println("Cached call duration: " + cachedDuration);

//     // ✅ Ensure cached call is at least 50% faster
//     assertTrue(cachedDuration < firstDuration * 0.5, "Redis should reduce response time");
// }

// }
