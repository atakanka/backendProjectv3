package com.example.backendProject;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /*public List<Product> getProductsByCreatedBy(String createdBy) {
        return productRepository.findAllByCreatedBy(createdBy);
    }*/

    public Product createProduct(Product product) {
        // Set the created by information based on the authenticated user
        // You can retrieve the user information from the security context or authentication token
        String createdBy = "authenticated_user";
        product.setCreatedBy(createdBy);
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product updatedProduct, String createdBy) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        if (!existingProduct.getCreatedBy().equals(createdBy)) {
            throw new IllegalArgumentException("You can only update your own products");
        }

        // Update the product fields
        existingProduct.setName(updatedProduct.getName());
        // Update other product fields as needed

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long productId, String createdBy) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        if (!existingProduct.getCreatedBy().equals(createdBy)) {
            throw new IllegalArgumentException("You can only delete your own products");
        }

        productRepository.delete(existingProduct);
    }
}
