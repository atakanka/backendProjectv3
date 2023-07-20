package com.example.backendProject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;


@SpringBootTest(classes = ProductControllerTest.class)
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void setup() {
        
        product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setDescription("Description of Product 1");
        product.setOwner(1L);
    }

    @Test
    public void testCreateProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Product 2\",\"description\":\"Description of Product 2\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
                //.andExpect(MockMvcResultMatchers.jsonPath("$[1].id").exists());
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Mockito.when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Description of Product 1"));
        // Add assertions for other fields as required
    }
    
    @Test
    public void testGetProductById() throws Exception {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description of Product 1"));
        // Add assertions for other fields as required
    }
    
    
    /*@Test
    public void testUpdateProduct() throws Exception {

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Product\",\"description\":\"Updated Description\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated Description"));
        // Add assertions for other fields as required
    }

    
    @Test
    public void testDeleteProduct() throws Exception {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }*/
}
