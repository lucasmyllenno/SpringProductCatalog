package com.spring.catalog.products.service;

import com.spring.catalog.products.entity.Product;
import com.spring.catalog.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("ProductService")
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(UUID id) {
        Optional<Product> written = repository.findById(id);
        return written.orElse(null);
    }

    public Product createProduct(Product product) {
        repository.save(product);
        Optional<Product> written = repository.findById(product.getId());
        return written.orElse(null);
    }

    public Product updateProduct(UUID id, Product product) {
        if (repository.existsById(id)) {
            repository.save(product);

            Optional<Product> written = repository.findById(id);
            return written.orElse(null);
        }
        return null;
    }

    public List<Product> getAllProductsByTerm(String term) {
        return repository.findAllByTerm(term);
    }

    public boolean deleteProduct(UUID id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }
}
