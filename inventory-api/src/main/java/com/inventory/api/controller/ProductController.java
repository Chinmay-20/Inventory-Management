// // package com.inventory.api.controller;

// // import com.inventory.api.dto.ProductDTO;
// // import com.inventory.api.service.ProductService;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.http.ResponseEntity;
// // import org.springframework.web.bind.annotation.*;
// // import jakarta.validation.Valid;
// // import org.springframework.web.bind.annotation.CrossOrigin;

// // import java.util.List;

// // @CrossOrigin(origins = "http://localhost:3000")  // ✅ Allow React frontend
// // @RestController
// // @RequestMapping("/api/products")
// // public class ProductController {

// //     @Autowired
// //     private ProductService productService;

// //     @GetMapping
// //     public List<ProductDTO> getAllProducts(@RequestParam(required = false)boolean refresh) {
// //         return productService.getAllProducts(refresh);
// //     }

// //     @GetMapping("/{id}")
// //     public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
// //         return ResponseEntity.ok(productService.getProductById(id));
// //     }

// //     @PostMapping
// //     public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
// //         return ResponseEntity.ok(productService.createProduct(productDTO));
// //     }

// //     @PutMapping("/{id}")
// //     public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
// //         return ResponseEntity.ok(productService.updateProduct(id, productDTO));
// //     }

// //     @DeleteMapping("/{id}")
// //     public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
// //         productService.deleteProduct(id);
// //         return ResponseEntity.noContent().build();
// //     }
// // }
// // /*
// //  * Swagger doc
// //  * http://localhost:8080/swagger-ui/index.html#/
// //  */

// package com.inventory.api.controller;

// import com.inventory.api.dto.ProductDTO;
// // import com.inventory.api.service;.KafkaProducerService;
// import com.inventory.api.service.ProductService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import jakarta.validation.Valid;
// import org.springframework.web.bind.annotation.CrossOrigin;

// import java.util.List;

// @CrossOrigin(origins = "http://localhost:3000")  // ✅ Allow React frontend
// @RestController
// @RequestMapping("/api/products")
// public class ProductController {

//     @Autowired
//     private ProductService kafkaProducerService;

//     @GetMapping
//     public ResponseEntity<String> getAllProducts() {
//         kafkaProducerService.sendProductEvent("FetchAll", null);
//         return ResponseEntity.ok("✅ Fetch request sent to Kafka. Check consumer logs.");
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<String> getProductById(@PathVariable Long id) {
//         kafkaProducerService.sendProductEvent("FetchById", id);
//         return ResponseEntity.ok("✅ Fetch request for Product ID: " + id + " sent to Kafka.");
//     }

//     @PostMapping
//     public ResponseEntity<String> createProduct(@Valid @RequestBody ProductDTO productDTO) {
//         kafkaProducerService.sendProductEvent("Created", productDTO);
//         return ResponseEntity.ok("✅ Create event sent for Product: " + productDTO.getName());
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
//         productDTO.setId(id);  // Ensure ID is set
//         kafkaProducerService.sendProductEvent("Updated", productDTO);
//         return ResponseEntity.ok("✅ Update event sent for Product ID: " + id);
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
//         kafkaProducerService.sendProductEvent("Deleted", id);
//         return ResponseEntity.ok("✅ Delete event sent for Product ID: " + id);
//     }
// }

package com.inventory.api.controller;

import com.inventory.api.dto.ProductDTO;
import com.inventory.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts(@RequestParam(required = false) boolean refresh) {
        return productService.getAllProducts(refresh);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
