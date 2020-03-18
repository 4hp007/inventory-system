package com.github._4hp007.inventory.controller;

import com.github._4hp007.inventory.model.Product;
import com.github._4hp007.inventory.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    ResponseEntity<Product> addProduct(@RequestBody @Valid Product product) {
        Product productResp = productService.addProduct(product);
        URI location = URI.create(String.format("/api/products/%d", productResp.getId()));
        return ResponseEntity.created(location)
                             .body(productResp);
    }

    /**
     * Api to search products by when they were added or removed. Start and endTime is to be provided to filter out
     * the results.
     *
     * @param byAddTime true: search by addTime, false: search by removeTime
     * @param startTime starting time for search interval
     * @param endTime   ending time for search interval
     * @return List of Products as per the input criteria
     */
    @ApiOperation(value = "Search Product",
            notes = "Api to search products by when they were added or removed. " +
                    "Start and End Time to be provided to filter out the results.")
    @GetMapping
    ResponseEntity<List<Product>> getAllProducts(
            @ApiParam("true: if to be searched by addTime, false: if to be searched by removeTime")
            @RequestParam boolean byAddTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseEntity.ok(productService.findAllProductsByTime(byAddTime, startTime, endTime));
    }

    @GetMapping("{productId}")
    ResponseEntity<Product> getProduct(@PathVariable long productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @DeleteMapping("{productId}")
    ResponseEntity<?> deleteProduct(@PathVariable long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent()
                             .build();
    }
}
