package com.spring.catalog.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.catalog.products.entity.Product;
import com.spring.catalog.products.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;
    private Map<String, Object> body;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.product = new Product("Product", "Description", 10.0F);
        this.body = new HashMap<>();
        body.put("name","Product");
        body.put("description","Description");
        body.put("price",10.0);
    }

    @Test
    public void getProducts_Success_UnitTest() throws Exception {
        Mockito.when(this.productService.getAllProducts())
            .thenReturn(Collections.singletonList(this.product));

        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/products")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    public void getProducts_NoContent_UnitTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/products")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    public void getProduct_Success_UnitTest() throws Exception {
        Mockito.when(this.productService.getProductById(this.product.getId()))
            .thenReturn(this.product);

        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/products/{id}", this.product.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    public void getProduct_NotFound_UnitTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/products/{id}", this.product.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    public void createProduct_Success_UnitTest() throws Exception {
        Mockito.when(this.productService.createProduct(
                this.objectMapper.convertValue(this.body, Product.class)))
            .thenReturn(this.product);

        this.mockMvc.perform(MockMvcRequestBuilders
            .post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(this.body))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    public void createProduct_BadRequest_UnitTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(null))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    public void updateProduct_Success_UnitTest() throws Exception {
        Mockito.when(this.productService.updateProduct(this.product.getId(), this.product))
            .thenReturn(this.product);

        this.mockMvc.perform(MockMvcRequestBuilders
            .put("/products/{id}", this.product.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(this.product))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    public void updateProduct_NotFound_UnitTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .put("/products/{id}", this.product.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(this.product))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    public void getAllProductsByTerm_Success_UnitTest() throws Exception {
        String term = "Product";
        Mockito.when(this.productService.getAllProductsByTerm(term))
            .thenReturn(Collections.singletonList(this.product));

        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/products/search/{term}", term)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    public void getAllProductsByTerm_NotFound_UnitTest() throws Exception {
        String term = "Product";
        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/products/search/{term}", term)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    public void deleteProduct_Success_UnitTest() throws Exception {
        Mockito.when(this.productService.deleteProduct(this.product.getId()))
            .thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders
            .delete("/products/{id}", this.product.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    public void deleteProduct_NotFound_UnitTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .delete("/products/{id}", this.product.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }
}
