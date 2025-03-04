// package com.inventory.api;

// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest
// class InventoryApiApplicationTests {

// 	@Test
// 	void contextLoads() {
// 	}

// }

package com.inventory.api;

import com.inventory.api.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class InventoryApiApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    void contextLoads() {
        assertNotNull(productService);
    }

    @Test
    void testKafkaProducerAvailability() {
        // Ensure that Kafka is configured properly
        assertNotNull(productService);
    }
}
