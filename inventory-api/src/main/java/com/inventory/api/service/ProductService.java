// package com.inventory.api.service;

// import com.inventory.api.dto.ProductDTO;
// import com.inventory.api.exception.ResourceNotFoundException;
// import com.inventory.api.model.Product;
// import com.inventory.api.repository.ProductRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.cache.annotation.CacheEvict;
// import org.springframework.cache.annotation.CachePut;
// import org.springframework.cache.annotation.Cacheable;
// import org.springframework.cache.CacheManager;
// import org.springframework.kafka.core.KafkaTemplate;

// import java.util.List;
// // import java.util.Optional;
// import java.util.stream.Collectors;

// @Service
// public class ProductService {

//     @Autowired
//     private ProductRepository productRepository;
    
//     @Autowired
//     private CacheManager cachemanager;

//     @Autowired
//     private KafkaTemplate<String, String> kafkaTemplate;

//     // Convert Entity to DTO
//     private ProductDTO mapToDTO(Product product) {
//         return new ProductDTO(product.getId(), product.getName(), product.getQuantity(), product.getPrice());
//     }

//     // Convert DTO to Entity
//     private Product mapToEntity(ProductDTO productDTO) {
//         return new Product(productDTO.getId(), productDTO.getName(), productDTO.getQuantity(), productDTO.getPrice());
//     }

//     @Cacheable(value = "products") // caches all products
//     public List<ProductDTO> getAllProducts(boolean refresh) {
//         if (refresh){
//             cachemanager.getCache("products").clear();
//         }
//         List<Product> products = productRepository.findAll();
//         return products.stream().map(this::mapToDTO).collect(Collectors.toList());
//     }

//     @Cacheable(value="product", key="#id") // caches individual products by ID
//     public ProductDTO getProductById(Long id) {
//         Product product = productRepository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
//         return mapToDTO(product);
//     }

//     @CacheEvict(value = "products", allEntries = true)
//     @CachePut(value = "product", key = "#result.id") // updates cache when a product is created
//     public ProductDTO createProduct(ProductDTO productDTO) {
//         Product product = mapToEntity(productDTO);
//         Product savedProduct = productRepository.save(product);

//         // kafka code 
//         kafkaTemplate.send("product-events", "Created: " + savedProduct.getId());
//         return mapToDTO(savedProduct);
//     }

//     @CacheEvict(value = "products", allEntries = true)
//     @CachePut(value = "product", key = "#result.id") // updates cache when a product is created
//     public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
//         Product existingProduct = productRepository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

//         existingProduct.setName(productDTO.getName());
//         existingProduct.setQuantity(productDTO.getQuantity());
//         existingProduct.setPrice(productDTO.getPrice());

//         Product updatedProduct = productRepository.save(existingProduct);
//         kafkaTemplate.send("product-events", "Updated: " + updatedProduct.getId());
//         return mapToDTO(updatedProduct);
//     }

//     @CacheEvict(value = "product", key = "#id")  // Removes cache when a product is deleted
//     public void deleteProduct(Long id) {
//         if (!productRepository.existsById(id)) {
//             throw new ResourceNotFoundException("Product not found with ID: " + id);
//         }
//         productRepository.deleteById(id);

//         kafkaTemplate.send("product-events", "Deleted: " + id);
//     }
// }


// package com.inventory.api.service;

// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.inventory.api.dto.ProductDTO;
// import com.inventory.api.exception.ResourceNotFoundException;
// import com.inventory.api.model.Product;
// import com.inventory.api.repository.ProductRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.stream.Collectors;

// @Service
// public class ProductService {

//     @Autowired
//     private KafkaTemplate<String, String> kafkaTemplate;

//     @Autowired
//     private ObjectMapper objectMapper;  // JSON Serializer

//     // Convert Entity to DTO
//     private ProductDTO mapToDTO(Product product) {
//         return new ProductDTO(product.getId(), product.getName(), product.getQuantity(), product.getPrice());
//     }

//     // Convert DTO to Entity
//     private Product mapToEntity(ProductDTO productDTO) {
//         return new Product(productDTO.getId(), productDTO.getName(), productDTO.getQuantity(), productDTO.getPrice());
//     }

//     public void createProduct(ProductDTO productDTO) throws JsonProcessingException {
//         String eventMessage = objectMapper.writeValueAsString(new ProductEvent("Created", productDTO));
//         kafkaTemplate.send("product-events", eventMessage);
//     }

//     public void updateProduct(Long id, ProductDTO productDTO) throws JsonProcessingException {
//         productDTO.setId(id);  // Ensure ID remains consistent
//         String eventMessage = objectMapper.writeValueAsString(new ProductEvent("Updated", productDTO));
//         kafkaTemplate.send("product-events", eventMessage);
//     }

//     public void deleteProduct(Long id) throws JsonProcessingException {
//         ProductDTO productDTO = new ProductDTO(id, "", 0, 0.0); // Minimal data for deletion
//         String eventMessage = objectMapper.writeValueAsString(new ProductEvent("Deleted", productDTO));
//         kafkaTemplate.send("product-events", eventMessage);
//     }

//     // Inner class to represent Kafka messages
//     public static class ProductEvent {
//         private String eventType;
//         private ProductDTO product;

//         public ProductEvent(String eventType, ProductDTO product) {
//             this.eventType = eventType;
//             this.product = product;
//         }

//         public String getEventType() {
//             return eventType;
//         }

//         public ProductDTO getProduct() {
//             return product;
//         }
//     }
// }

// package com.inventory.api.kafka;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.inventory.api.dto.ProductDTO;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.stereotype.Service;

// @Service
// public class KafkaProducerService {

//     @Autowired
//     private KafkaTemplate<String, String> kafkaTemplate;

//     @Autowired
//     private ObjectMapper objectMapper;  // JSON Serializer

//     public void sendProductEvent(String eventType, Object payload) {
//         try {
//             String message = objectMapper.writeValueAsString(new ProductEvent(eventType, payload));
//             kafkaTemplate.send("product-events", message);
//             System.out.println("✅ Kafka Producer: Sent Event - " + eventType);
//         } catch (Exception e) {
//             System.err.println("❌ Kafka Producer Error: " + e.getMessage());
//         }
//     }

//     // Inner class to structure Kafka messages
//     private static class ProductEvent {
//         private String eventType;
//         private Object product;

//         public ProductEvent(String eventType, Object product) {
//             this.eventType = eventType;
//             this.product = product;
//         }

//         public String getEventType() {
//             return eventType;
//         }

//         public Object getProduct() {
//             return product;
//         }
//     }
// }

package com.inventory.api.service;

import com.inventory.api.dto.ProductDTO;
import com.inventory.api.exception.ResourceNotFoundException;
import com.inventory.api.model.Product;
import com.inventory.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.CacheManager;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private KafkaTemplate<String, ProductDTO> kafkaTemplate;  // Sends JSON product events

    private ProductDTO mapToDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getQuantity(), product.getPrice());
    }

    private Product mapToEntity(ProductDTO productDTO) {
        return new Product(productDTO.getId(), productDTO.getName(), productDTO.getQuantity(), productDTO.getPrice());
    }

    @Cacheable(value = "products")
    public List<ProductDTO> getAllProducts(boolean refresh) {
        if (refresh) {
            cacheManager.getCache("products").clear();
        }
        return productRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Cacheable(value = "product", key = "#id")
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return mapToDTO(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    @CachePut(value = "product", key = "#result.id")
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = mapToEntity(productDTO);
        Product savedProduct = productRepository.save(product);

        // ✅ Publish Event to Kafka
        kafkaTemplate.send("product-events", savedProduct.getId().toString(), productDTO);
        return mapToDTO(savedProduct);
    }

    @CacheEvict(value = "products", allEntries = true)
    @CachePut(value = "product", key = "#result.id")
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));

        existingProduct.setName(productDTO.getName());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setPrice(productDTO.getPrice());

        Product updatedProduct = productRepository.save(existingProduct);

        // ✅ Publish Event to Kafka
        kafkaTemplate.send("product-events", updatedProduct.getId().toString(), productDTO);
        return mapToDTO(updatedProduct);
    }

    @CacheEvict(value = "product", key = "#id")
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);

        // ✅ Publish Delete Event to Kafka
        kafkaTemplate.send("product-events", id.toString(), null);
    }
}
