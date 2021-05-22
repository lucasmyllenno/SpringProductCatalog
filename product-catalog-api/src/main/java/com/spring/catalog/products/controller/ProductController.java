package com.spring.catalog.products.controller;

import com.spring.catalog.products.entity.Product;
import com.spring.catalog.products.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    @Resource(name = "ProductService")
    private ProductService service;

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> response = service.getAllProducts();

        HttpStatus httpStatus
                = response.isEmpty() ? HttpStatus.NO_CONTENT
                : HttpStatus.OK;

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") UUID id) {
        Product response = service.getProductById(id);

        HttpStatus httpStatus
                = response == null ? HttpStatus.NOT_FOUND
                : HttpStatus.OK;

        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product category) {
        Product response = service.createProduct(category);

        HttpStatus httpStatus
                = response == null ? HttpStatus.BAD_REQUEST
                : HttpStatus.OK;

        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") UUID id,
                                                 @RequestBody Product category) {
        Product response = service.updateProduct(id, category);

        HttpStatus httpStatus
                = response == null ? HttpStatus.NOT_FOUND
                : HttpStatus.OK;

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/search/{term}")
    public ResponseEntity<List<Product>> getAllProductsByTerm(@PathVariable(value = "term") String term) {
        List<Product> response = service.getAllProductsByTerm(term);

        HttpStatus httpStatus
                = response.isEmpty() ? HttpStatus.NOT_FOUND
                : HttpStatus.OK;

        return new ResponseEntity<>(response, httpStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable(value = "id") UUID id) {
        boolean response = service.deleteProduct(id);

        HttpStatus httpStatus
                = !response ? HttpStatus.NOT_FOUND
                : HttpStatus.OK;

        return new ResponseEntity<>(httpStatus);
    }
}
