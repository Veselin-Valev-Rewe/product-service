package com.example.product_service.controller;

import com.example.product_service.dto.CreateProductDto;
import com.example.product_service.dto.ProductDto;
import com.example.product_service.dto.UpdateProductDto;
import com.example.product_service.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getProducts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable @Positive int id) {
        var product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid CreateProductDto ProductDto) {
        var product = productService.createProduct(ProductDto);
        URI location = URI.create("/api/products/" + product.getId());
        return ResponseEntity.created(location).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable @Positive int id, @RequestBody @Valid UpdateProductDto ProductDto) {
        var product = productService.updateProduct(id, ProductDto);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @Positive int id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
