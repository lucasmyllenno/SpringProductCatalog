package com.spring.catalog.products;

import com.spring.catalog.products.entity.Product;
import com.spring.catalog.products.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductIntegrationTests {

    @Autowired
    private ProductRepository repository;

    private Product currentProduct;

    @BeforeEach
    void initUseCase() {
        this.repository.deleteAll();
        this.currentProduct = new Product("Product", "Description", 10.0F);
    }

    @Test
    public void getAllProductsTest() {
        this.repository.save(this.currentProduct);
        List<Product> list = this.repository.findAll();
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void createProductTest() {
        this.repository.save(this.currentProduct);
        boolean isValidProduct = this.repository.existsById(this.currentProduct.getId());
        Assertions.assertTrue(isValidProduct);
    }

    @Test
    public void updateProductTest() {
        this.repository.save(this.currentProduct);
        Assertions.assertEquals(this.currentProduct.getName(), "Product");
        Assertions.assertEquals(this.currentProduct.getDescription(), "Description");
        Assertions.assertEquals(this.currentProduct.getPrice(), 10.0F);

        this.currentProduct.setName("Other Product");
        this.currentProduct.setDescription("Other Description");
        this.currentProduct.setPrice(5.0F);
        this.repository.save(this.currentProduct);

        Optional<Product> written = this.repository.findById(this.currentProduct.getId());
        Assertions.assertEquals(written.get().getName(), "Other Product");
        Assertions.assertEquals(written.get().getDescription(), "Other Description");
        Assertions.assertEquals(written.get().getPrice(), 5.0F);
    }

    @Test
    public void getAllProductsByTermTest() {
        this.repository.save(this.currentProduct);

        final String term = "Prod";
        List<Product> list = this.repository.findAllByTerm(term);
        Assertions.assertFalse(list.isEmpty());

        final String term2 = "Temp";
        List<Product> list2 = this.repository.findAllByTerm(term2);
        Assertions.assertTrue(list2.isEmpty());
    }

    @Test
    public void deleteProductTest() {
        this.repository.save(this.currentProduct);
        boolean isValidProduct = this.repository.existsById(this.currentProduct.getId());
        Assertions.assertTrue(isValidProduct);

        this.repository.deleteById(this.currentProduct.getId());
        isValidProduct = this.repository.existsById(this.currentProduct.getId());
        Assertions.assertFalse(isValidProduct);
    }
}
