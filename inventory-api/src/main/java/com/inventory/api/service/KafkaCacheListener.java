// // package com.inventory.api.service;

// // import org.springframework.kafka.annotation.KafkaListener;
// // import org.springframework.stereotype.Service;
// // import org.springframework.cache.CacheManager;

// // @Service
// // public class KafkaCacheListener {
    
// //     private final CacheManager cacheManager;

// //     public KafkaCacheListener(CacheManager cacheManager){
// //         this.cacheManager = cacheManager;
// //     }

// //     @KafkaListener(topics = "product-events", groupId = "inventory-group")
// //     public void handleProductEvent(String message){

// //         System.out.println("Received Kafka message: " + message);

// //         if(message.startsWith("Created") || message.startsWith("Updated")){
// //             String productId = message.split(": ")[1];
// //             cacheManager.getCache("product").evict(productId);
// //         }
// //         else if(message.startsWith("Deleted")){
// //             cacheManager.getCache("products").clear();
// //         }
// //     }
// // }

// // package com.inventory.api.service;

// // import com.fasterxml.jackson.databind.ObjectMapper;
// // import com.inventory.api.dto.ProductDTO;
// // import com.inventory.api.model.Product;
// // import com.inventory.api.repository.ProductRepository;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.cache.CacheManager;
// // import org.springframework.kafka.annotation.KafkaListener;
// // import org.springframework.stereotype.Service;
// // import org.springframework.transaction.annotation.Transactional;

// // @Service
// // public class KafkaCacheListener {

// //     @Autowired
// //     private ProductRepository productRepository;

// //     @Autowired
// //     private ObjectMapper objectMapper; // JSON Deserializer

// //     @Autowired
// //     private CacheManager cacheManager; // Caching Service

// //     @KafkaListener(topics = "product-events", groupId = "inventory-group")
// //     @Transactional
// //     public void listenProductEvents(String message) {
// //         try {
// //             ProductEvent event = objectMapper.readValue(message, ProductEvent.class);
// //             Product product = mapToEntity(event.getProduct());

// //             switch (event.getEventType()) {
// //                 case "Created":
// //                     productRepository.save(product);
// //                     updateCache(product);
// //                     System.out.println("Kafka Consumer: Created Product ID: " + product.getId());
// //                     break;
// //                 case "Updated":
// //                     Product existingProduct = productRepository.findById(product.getId())
// //                             .orElseThrow(() -> new RuntimeException("Product not found: " + product.getId()));
// //                     existingProduct.setName(product.getName());
// //                     existingProduct.setQuantity(product.getQuantity());
// //                     existingProduct.setPrice(product.getPrice());
// //                     productRepository.save(existingProduct);
// //                     updateCache(existingProduct);
// //                     System.out.println("Kafka Consumer: Updated Product ID: " + product.getId());
// //                     break;
// //                 case "Deleted":
// //                     productRepository.deleteById(product.getId());
// //                     invalidateCache(product.getId());
// //                     System.out.println("Kafka Consumer: Deleted Product ID: " + product.getId());
// //                     break;
// //                 default:
// //                     System.err.println("⚠ Unknown Kafka Event: " + event.getEventType());
// //             }
// //         } catch (Exception e) {
// //             System.err.println("Kafka Consumer Error: " + e.getMessage());
// //         }
// //     }

// //     // ✅ Updates the cache after product creation/update
// //     private void updateCache(Product product) {
// //         if (cacheManager.getCache("product") != null) {
// //             cacheManager.getCache("product").put(product.getId(), product);
// //         }
// //         if (cacheManager.getCache("products") != null) {
// //             cacheManager.getCache("products").clear(); // Refresh product list cache
// //         }
// //     }

// //     // Removes product from cache after deletion
// //     private void invalidateCache(Long productId) {
// //         if (cacheManager.getCache("product") != null) {
// //             cacheManager.getCache("product").evict(productId);
// //         }
// //         if (cacheManager.getCache("products") != null) {
// //             cacheManager.getCache("products").clear(); // Refresh product list cache
// //         }
// //     }

// //     // Helper method to convert DTO to Entity
// //     private Product mapToEntity(ProductDTO productDTO) {
// //         return new Product(productDTO.getId(), productDTO.getName(), productDTO.getQuantity(), productDTO.getPrice());
// //     }

// //     // Inner class to parse JSON events
// //     private static class ProductEvent {
// //         private String eventType;
// //         private ProductDTO product;

// //         public String getEventType() {
// //             return eventType;
// //         }

// //         public ProductDTO getProduct() {
// //             return product;
// //         }
// //     }
// // }

// package com.inventory.api.service;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.inventory.api.dto.ProductDTO;
// import com.inventory.api.model.Product;
// import com.inventory.api.repository.ProductRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.cache.CacheManager;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// @Service
// public class KafkaCacheListener {

//     @Autowired
//     private ProductRepository productRepository;

//     @Autowired
//     private ObjectMapper objectMapper; // JSON Deserializer

//     @Autowired
//     private CacheManager cacheManager; // Caching Service

//     @KafkaListener(topics = "product-events", groupId = "inventory-group")
//     @Transactional
//     public void listenProductEvents(String message) {
//         try {
//             ProductEvent event = objectMapper.readValue(message, ProductEvent.class);

//             switch (event.getEventType()) {
//                 case "Created":
//                     Product newProduct = mapToEntity((ProductDTO) event.getProduct());
//                     productRepository.save(newProduct);
//                     updateCache(newProduct);
//                     System.out.println("✅ Kafka Consumer: Created Product ID: " + newProduct.getId());
//                     break;
//                 case "Updated":
//                     Product updatedProduct = productRepository.findById(event.getProductId())
//                             .orElseThrow(() -> new RuntimeException("Product not found: " + event.getProductId()));
//                     ProductDTO productDTO = (ProductDTO) event.getProduct();
//                     updatedProduct.setName(productDTO.getName());
//                     updatedProduct.setQuantity(productDTO.getQuantity());
//                     updatedProduct.setPrice(productDTO.getPrice());
//                     productRepository.save(updatedProduct);
//                     updateCache(updatedProduct);
//                     System.out.println("✅ Kafka Consumer: Updated Product ID: " + updatedProduct.getId());
//                     break;
//                 case "Deleted":
//                     productRepository.deleteById(event.getProductId());
//                     invalidateCache(event.getProductId());
//                     System.out.println("❌ Kafka Consumer: Deleted Product ID: " + event.getProductId());
//                     break;
//                 case "FetchAll":
//                     System.out.println("📥 Fetch All Request - Not Implemented Yet");
//                     break;
//                 case "FetchById":
//                     System.out.println("📥 Fetch Product ID: " + event.getProductId() + " - Not Implemented Yet");
//                     break;
//                 default:
//                     System.err.println("⚠ Unknown Kafka Event: " + event.getEventType());
//             }
//         } catch (Exception e) {
//             System.err.println("❌ Kafka Consumer Error: " + e.getMessage());
//         }
//     }

//     private void updateCache(Product product) {
//         if (cacheManager.getCache("product") != null) {
//             cacheManager.getCache("product").put(product.getId(), product);
//         }
//         if (cacheManager.getCache("products") != null) {
//             cacheManager.getCache("products").clear();
//         }
//     }

//     private void invalidateCache(Long productId) {
//         if (cacheManager.getCache("product") != null) {
//             cacheManager.getCache("product").evict(productId);
//         }
//         if (cacheManager.getCache("products") != null) {
//             cacheManager.getCache("products").clear();
//         }
//     }

//     private Product mapToEntity(ProductDTO productDTO) {
//         return new Product(productDTO.getId(), productDTO.getName(), productDTO.getQuantity(), productDTO.getPrice());
//     }

//     private static class ProductEvent {
//         private String eventType;
//         private Object product;
//         private Long productId;

//         public String getEventType() {
//             return eventType;
//         }

//         public Object getProduct() {
//             return product;
//         }

//         public Long getProductId() {
//             return productId;
//         }
//     }
// }

package com.inventory.api.service;

import com.inventory.api.dto.ProductDTO;
import com.inventory.api.model.Product;
import com.inventory.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaCacheListener {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CacheManager cacheManager;

    @KafkaListener(topics = "product-events", groupId = "inventory-group")
    public void listen(ProductDTO productDTO) {
        if (productDTO != null) {
            // ✅ Update database
            Product product = new Product(productDTO.getId(), productDTO.getName(), productDTO.getQuantity(), productDTO.getPrice());
            productRepository.save(product);

            // ✅ Update cache
            cacheManager.getCache("product").put(product.getId(), productDTO);
            System.out.println("✅ Product Updated in DB & Cache: " + productDTO.getId());
        } else {
            System.out.println("❌ Received null product, ignoring.");
        }
    }
}
