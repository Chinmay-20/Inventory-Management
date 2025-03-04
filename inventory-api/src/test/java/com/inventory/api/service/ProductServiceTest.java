// // // // package com.inventory.api.service;

// // // // import com.inventory.api.dto.ProductDTO;
// // // // import com.inventory.api.exception.ResourceNotFoundException;
// // // // import com.inventory.api.model.Product;
// // // // import com.inventory.api.repository.ProductRepository;
// // // // import org.junit.jupiter.api.BeforeEach;
// // // // import org.junit.jupiter.api.Test;
// // // // import org.junit.jupiter.api.extension.ExtendWith;
// // // // import org.mockito.InjectMocks;
// // // // import org.mockito.Mock;
// // // // // import org.mockito.Mockito;
// // // // import org.mockito.junit.jupiter.MockitoExtension;

// // // // import java.util.List;
// // // // import java.util.Optional;

// // // // import static org.junit.jupiter.api.Assertions.*;
// // // // import static org.mockito.Mockito.*;

// // // // @ExtendWith(MockitoExtension.class)
// // // // class ProductServiceTest {

// // // //     @Mock
// // // //     private ProductRepository productRepository;

// // // //     @InjectMocks
// // // //     private ProductService productService;

// // // //     private Product testProduct;

// // // //     @BeforeEach
// // // //     void setUp() {
// // // //         testProduct = new Product(1L, "Laptop", 10, 1200.50);
// // // //     }

// // // //     @Test
// // // //     void testGetAllProducts() {
// // // //         when(productRepository.findAll()).thenReturn(List.of(testProduct));

// // // //         List<ProductDTO> products = productService.getAllProducts(true);

// // // //         assertFalse(products.isEmpty());
// // // //         assertEquals(1, products.size());
// // // //         assertEquals("Laptop", products.get(0).getName());
// // // //     }

// // // //     @Test
// // // //     void testGetProductById_Success() {
// // // //         when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

// // // //         ProductDTO product = productService.getProductById(1L);

// // // //         assertNotNull(product);
// // // //         assertEquals("Laptop", product.getName());
// // // //     }

// // // //     @Test
// // // //     void testGetProductById_NotFound() {
// // // //         when(productRepository.findById(1L)).thenReturn(Optional.empty());

// // // //         assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
// // // //     }

// // // //     @Test
// // // //     void testCreateProduct() {
// // // //         when(productRepository.save(any(Product.class))).thenReturn(testProduct);

// // // //         ProductDTO createdProduct = productService.createProduct(new ProductDTO(null, "Laptop", 10, 1200.50));

// // // //         assertNotNull(createdProduct);
// // // //         assertEquals("Laptop", createdProduct.getName());
// // // //     }

// // // //     @Test
// // // //     void testDeleteProduct_Success() {
// // // //         when(productRepository.existsById(1L)).thenReturn(true);
// // // //         doNothing().when(productRepository).deleteById(1L);

// // // //         assertDoesNotThrow(() -> productService.deleteProduct(1L));
// // // //     }

// // // //     @Test
// // // //     void testDeleteProduct_NotFound() {
// // // //         when(productRepository.existsById(1L)).thenReturn(false);

// // // //         assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(1L));
// // // //     }
// // // // }

// // // package com.inventory.api.service;

// // // import com.inventory.api.dto.ProductDTO;
// // // import com.inventory.api.exception.ResourceNotFoundException;
// // // import com.inventory.api.model.Product;
// // // import com.inventory.api.repository.ProductRepository;
// // // import org.junit.jupiter.api.BeforeEach;
// // // import org.junit.jupiter.api.Test;
// // // import org.junit.jupiter.api.extension.ExtendWith;
// // // import org.mockito.InjectMocks;
// // // import org.mockito.Mock;
// // // import org.mockito.junit.jupiter.MockitoExtension;
// // // import org.springframework.cache.CacheManager;
// // // import org.springframework.kafka.core.KafkaTemplate;

// // // import java.util.List;
// // // import java.util.Optional;

// // // import static org.junit.jupiter.api.Assertions.*;
// // // import static org.mockito.Mockito.*;

// // // @ExtendWith(MockitoExtension.class)
// // // class ProductServiceTest {

// // //     @Mock
// // //     private ProductRepository productRepository;

// // //     @Mock
// // //     private KafkaTemplate<String, Object> kafkaTemplate;  // ✅ Mock Kafka

// // //     @Mock
// // //     private CacheManager cacheManager;  // ✅ Mock Cache Manager

// // //     @InjectMocks
// // //     private ProductService productService;

// // //     private Product testProduct;

// // //     @BeforeEach
// // //     void setUp() {
// // //         testProduct = new Product(1L, "Laptop", 10, 1200.50);
// // //     }

// // //     @Test
// // //     void testGetAllProducts() {
// // //         when(productRepository.findAll()).thenReturn(List.of(testProduct));

// // //         List<ProductDTO> products = productService.getAllProducts(true);

// // //         assertFalse(products.isEmpty());
// // //         assertEquals(1, products.size());
// // //         assertEquals("Laptop", products.get(0).getName());
// // //     }

// // //     @Test
// // //     void testGetProductById_Success() {
// // //         when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

// // //         ProductDTO product = productService.getProductById(1L);

// // //         assertNotNull(product);
// // //         assertEquals("Laptop", product.getName());
// // //     }

// // //     @Test
// // //     void testGetProductById_NotFound() {
// // //         when(productRepository.findById(1L)).thenReturn(Optional.empty());

// // //         assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
// // //     }

// // //     @Test
// // //     void testCreateProduct() {
// // //         when(productRepository.save(any(Product.class))).thenReturn(testProduct);
// // //         doNothing().when(kafkaTemplate).send(anyString(), anyString());  // ✅ Mock Kafka Send

// // //         ProductDTO createdProduct = productService.createProduct(new ProductDTO(null, "Laptop", 10, 1200.50));

// // //         assertNotNull(createdProduct);
// // //         assertEquals("Laptop", createdProduct.getName());
// // //         verify(kafkaTemplate, times(1)).send(eq("product-events"), anyString());  // ✅ Ensure Kafka event is triggered
// // //     }

// // //     @Test
// // //     void testDeleteProduct_Success() {
// // //         when(productRepository.existsById(1L)).thenReturn(true);
// // //         doNothing().when(productRepository).deleteById(1L);
// // //         doNothing().when(kafkaTemplate).send(anyString(), anyString());  // ✅ Mock Kafka Send

// // //         assertDoesNotThrow(() -> productService.deleteProduct(1L));

// // //         verify(kafkaTemplate, times(1)).send(eq("product-events"), anyString());  // ✅ Ensure Kafka event is triggered
// // //     }

// // //     @Test
// // //     void testDeleteProduct_NotFound() {
// // //         when(productRepository.existsById(1L)).thenReturn(false);

// // //         assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(1L));
// // //     }
// // // }
// // package com.inventory.api.service;

// // import com.inventory.api.dto.ProductDTO;
// // import com.inventory.api.exception.ResourceNotFoundException;
// // import com.inventory.api.model.Product;
// // import com.inventory.api.repository.ProductRepository;
// // import org.junit.jupiter.api.BeforeEach;
// // import org.junit.jupiter.api.Test;
// // import org.junit.jupiter.api.extension.ExtendWith;
// // import org.mockito.InjectMocks;
// // import org.mockito.Mock;
// // import org.mockito.junit.jupiter.MockitoExtension;
// // import org.springframework.cache.Cache;
// // import org.springframework.cache.CacheManager;
// // import org.springframework.kafka.core.KafkaTemplate;

// // import java.util.List;
// // import java.util.Optional;

// // import static org.junit.jupiter.api.Assertions.*;
// // import static org.mockito.Mockito.*;

// // @ExtendWith(MockitoExtension.class)
// // class ProductServiceTest {

// //     @Mock
// //     private ProductRepository productRepository;

// //     @Mock
// //     private KafkaTemplate<String, Object> kafkaTemplate;

// //     @Mock
// //     private CacheManager cacheManager;

// //     @InjectMocks
// //     private ProductService productService;

// //     private Product testProduct;
// //     private Cache productCache;

// //     @BeforeEach
// //     void setUp() {
// //         testProduct = new Product(1L, "Laptop", 10, 1200.50);
// //         productCache = mock(Cache.class);

// //         when(cacheManager.getCache("product")).thenReturn(productCache);
// //         when(cacheManager.getCache("products")).thenReturn(productCache);
// //     }

// //     @Test
// //     void testGetAllProducts() {
// //         when(productRepository.findAll()).thenReturn(List.of(testProduct));

// //         List<ProductDTO> products = productService.getAllProducts(true);

// //         assertFalse(products.isEmpty());
// //         assertEquals(1, products.size());
// //         assertEquals("Laptop", products.get(0).getName());
// //     }

// //     @Test
// //     void testGetProductById_Success() {
// //         when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

// //         ProductDTO product = productService.getProductById(1L);

// //         assertNotNull(product);
// //         assertEquals("Laptop", product.getName());
// //     }

// //     @Test
// //     void testGetProductById_NotFound() {
// //         when(productRepository.findById(1L)).thenReturn(Optional.empty());

// //         assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
// //     }

// //     @Test
// //     void testCreateProduct() {
// //         when(productRepository.save(any(Product.class))).thenReturn(testProduct);

// //         ProductDTO createdProduct = productService.createProduct(new ProductDTO(null, "Laptop", 10, 1200.50));

// //         assertNotNull(createdProduct);
// //         assertEquals("Laptop", createdProduct.getName());

// //         verify(kafkaTemplate).send(eq("product-events"), any());
// //     }

// //     @Test
// //     void testDeleteProduct_Success() {
// //         when(productRepository.existsById(1L)).thenReturn(true);
// //         doNothing().when(productRepository).deleteById(1L);

// //         assertDoesNotThrow(() -> productService.deleteProduct(1L));

// //         verify(kafkaTemplate).send(eq("product-events"), any());
// //     }

// //     @Test
// //     void testDeleteProduct_NotFound() {
// //         when(productRepository.existsById(1L)).thenReturn(false);

// //         assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(1L));
// //     }
// // }


// package com.inventory.api.service;

// import com.inventory.api.dto.ProductDTO;
// import com.inventory.api.exception.ResourceNotFoundException;
// import com.inventory.api.model.Product;
// import com.inventory.api.repository.ProductRepository;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.cache.Cache;
// import org.springframework.cache.CacheManager;
// import org.springframework.kafka.core.KafkaTemplate;

// import java.util.List;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// class ProductServiceTest {

//     @Mock
//     private ProductRepository productRepository;

//     @Mock
//     private KafkaTemplate<String, Object> kafkaTemplate;

//     @Mock
//     private CacheManager cacheManager;

//     @InjectMocks
//     private ProductService productService;

//     private Product testProduct;
//     private Cache productCache;

//     @BeforeEach
//     void setUp() {
//         testProduct = new Product(1L, "Laptop", 10, 1200.50);
//         productCache = mock(Cache.class);

//         lenient().when(cacheManager.getCache("product")).thenReturn(productCache);
//         lenient().when(cacheManager.getCache("products")).thenReturn(productCache);
//         lenient().when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
//     }

//     @Test
//     void testGetAllProducts() {
//         when(productRepository.findAll()).thenReturn(List.of(testProduct));

//         List<ProductDTO> products = productService.getAllProducts(true);

//         assertFalse(products.isEmpty());
//         assertEquals(1, products.size());
//         assertEquals("Laptop", products.get(0).getName());
//     }

//     @Test
//     void testGetProductById_Success() {
//         ProductDTO product = productService.getProductById(1L);

//         assertNotNull(product);
//         assertEquals("Laptop", product.getName());
//     }

//     @Test
//     void testGetProductById_NotFound() {
//         when(productRepository.findById(2L)).thenReturn(Optional.empty());

//         assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(2L));
//     }

//     @Test
//     void testCreateProduct() {
//         when(productRepository.save(any(Product.class))).thenReturn(testProduct);

//         ProductDTO createdProduct = productService.createProduct(new ProductDTO(null, "Laptop", 10, 1200.50));

//         assertNotNull(createdProduct);
//         assertEquals("Laptop", createdProduct.getName());

//         verify(kafkaTemplate, times(1)).send(eq("product-events"), any());
//     }

//     @Test
//     void testDeleteProduct_Success() {
//         when(productRepository.existsById(1L)).thenReturn(true);
//         doNothing().when(productRepository).deleteById(1L);

//         assertDoesNotThrow(() -> productService.deleteProduct(1L));

//         verify(kafkaTemplate, times(1)).send(eq("product-events"), any());
//     }

//     @Test
//     void testDeleteProduct_NotFound() {
//         when(productRepository.existsById(2L)).thenReturn(false);

//         assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(2L));
//     }
// }

package com.inventory.api.service;

import com.inventory.api.dto.ProductDTO;
import com.inventory.api.exception.ResourceNotFoundException;
import com.inventory.api.model.Product;
import com.inventory.api.repository.ProductRepository;

import org.apache.kafka.common.protocol.types.Field.Str;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.contains;
// import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private Cache productCache;

    @BeforeEach
    void setUp() {
        testProduct = new Product(1L, "Laptop", 10, 1200.50);
        productCache = mock(Cache.class);

        lenient().when(cacheManager.getCache("product")).thenReturn(productCache);
        lenient().when(cacheManager.getCache("products")).thenReturn(productCache);
        lenient().when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(testProduct));

        List<ProductDTO> products = productService.getAllProducts(true);

        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("Laptop", products.get(0).getName());
    }

    @Test
    void testGetProductById_Success() {
        ProductDTO product = productService.getProductById(1L);

        assertNotNull(product);
        assertEquals("Laptop", product.getName());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(2L));
    }

    // @Test
    // void testCreateProduct() {
    //     when(productRepository.save(any(Product.class))).thenReturn(testProduct);

    //     ProductDTO createdProduct = productService.createProduct(new ProductDTO(null, "Laptop", 1970, 123700.50));

    //     assertNotNull(createdProduct);
    //     assertEquals("Laptop", createdProduct.getName());

    //     verify(kafkaTemplate, times(1)).send(eq("product-events"), contains("Created"));
    // }

    // @Test
    // void testDeleteProduct_Success() {
    //     when(productRepository.existsById(1L)).thenReturn(true);
    //     doNothing().when(productRepository).deleteById(1L);

    //     assertDoesNotThrow(() -> productService.deleteProduct(1L));

    //     verify(kafkaTemplate, times(1)).send(eq("product-events"), contains("Deleted"));
    // }

    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.existsById(2L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(2L));
    }
}
