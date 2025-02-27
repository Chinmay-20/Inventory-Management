package com.inventory.api.performance;

import com.inventory.api.dto.ProductDTO;
import com.inventory.api.repository.ProductRepository;
import com.inventory.api.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import org.junit.jupiter.api.BeforeEach;

@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)  
public class ProductLoadTest {

    @Autowired
    private ProductService productService;
   
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;


    @BeforeEach
    void setup() {
        productRepository.deleteAll(); // ✅ Clears existing products before each test
    }

    @Test
    void testBulkProductCreation() {
        int numProducts = 10000;  // ✅ Test with 10,000 products

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition()); // ✅ Start a transaction
        
        for (int i = 0; i < numProducts; i++) { 
            ProductDTO product = new ProductDTO(null, "Product_" + i, 10000, 500.0);
            productService.createProduct(product);
            
            if (i % 1000 == 0) {
                System.out.println("Inserted: " + i + " products...");
            }
    
        }

        transactionManager.commit(status);

        int totalProducts = productService.getAllProducts().size();
        System.out.println("Total Products in DB: " + totalProducts);

        // Check if 10,000 products exist in DB
        assertEquals(numProducts, productService.getAllProducts().size());
    }
}
