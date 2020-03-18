package com.github._4hp007.inventory.service;

import com.github._4hp007.inventory.exception.ProductNotFoundException;
import com.github._4hp007.inventory.model.Product;
import com.github._4hp007.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(long id) {
        Product product = productRepository.findById(id)
                                           .orElseThrow(() -> new ProductNotFoundException(
                                                   String.format("Product not found for ID %d", id)));
        if (product.getRemoveTime() != null)
            throw new ProductNotFoundException(String.format("Product has been removed for ID %d", id));
        return product;
    }

    public void deleteProduct(long id) {
        Product product = productRepository.findById(id)
                                           .orElseThrow(() -> new ProductNotFoundException(
                                                   String.format("Product not found for ID %d", id)));
        if (product.getRemoveTime() != null) {
            throw new ProductNotFoundException(String.format("Product already removed for ID %d", id));
        }
        productRepository.deleteById(id);
    }

    public List<Product> findAllProductsByTime(boolean byAddTime, LocalDateTime startTime, LocalDateTime endTime) {
        Predicate<Product> predicate;
        Function<Product, LocalDateTime> comparatorKey;
        if (byAddTime) {
            predicate = p -> p.getRemoveTime() == null &&
                    startTime.compareTo(p.getAddTime()) <= 0 &&
                    endTime.compareTo(p.getAddTime()) >= 0;
            comparatorKey = Product::getAddTime;
        }
        else {
            predicate = p -> p.getRemoveTime() != null &&
                    startTime.compareTo(p.getRemoveTime()) <= 0 &&
                    endTime.compareTo(p.getRemoveTime()) >= 0;
            comparatorKey = Product::getRemoveTime;
        }
        return productRepository.findAll()
                                .stream()
                                .filter(predicate)
                                .sorted(Comparator.comparing(comparatorKey))
                                .collect(Collectors.toList());
    }
}
