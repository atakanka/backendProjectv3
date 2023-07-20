package com.example.backendProject;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

// Importing required classes
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    private Object authenticatedClientId;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product updatedProduct) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }
        // Check if the authenticated client is the owner of the product
        // Implement your own logic to authenticate the client
        if (!existingProduct.getOwner().equals(authenticatedClientId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // Update the product fields
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        // Update other fields as per your requirements

        Product savedProduct = productRepository.save(existingProduct);
        return ResponseEntity.ok(savedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }
        // Check if the authenticated client is the owner of the product
        // Implement your own logic to authenticate the client
        if (!existingProduct.getOwner().equals(authenticatedClientId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        productRepository.delete(existingProduct);
        return ResponseEntity.noContent().build();
    }
}