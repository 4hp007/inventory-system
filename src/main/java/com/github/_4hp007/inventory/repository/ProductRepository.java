package com.github._4hp007.inventory.repository;

import com.github._4hp007.inventory.model.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class ProductRepository {

    private static final Map<Long, Product> PRODUCT_MAP = new HashMap<>();
    private static final AtomicLong ids = new AtomicLong(1);

    public Product save(Product product) {
        long id = ids.getAndIncrement();
        product.setId(id);
        product.setAddTime(LocalDateTime.now());
        PRODUCT_MAP.put(id, new Product(product));
        return product;
    }

    public Optional<Product> findById(long id) {
        Product product = PRODUCT_MAP.get(id);
        if (product == null)
            return Optional.empty();
        return Optional.of(new Product(product));
    }

    public void deleteById(long id) {
        Product product = PRODUCT_MAP.get(id);
        product.setRemoveTime(LocalDateTime.now());
    }

    public List<Product> findAll() {
        return PRODUCT_MAP.values()
                          .stream()
                          .map(Product::new)
                          .collect(Collectors.toList());
    }
}
