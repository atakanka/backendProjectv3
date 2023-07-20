package com.example.backendProject;

// Importing required classes 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Long owner;

    // Add other product fields as needed (e.g., price, description)

    // Add getters and setters
    public Long getId() {
        return id;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long ownerIDLong){
        this.owner = ownerIDLong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedBy(String createdBy) {
    }

    public Object getCreatedBy() {
        return owner;
    }

    public void setId(long id) {
        this.id = id;
    }
}
