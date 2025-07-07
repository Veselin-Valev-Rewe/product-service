package com.example.product_service.service;

import com.example.product_service.dto.CreateProductDto;
import com.example.product_service.dto.UpdateProductDto;
import com.example.product_service.dto.ProductDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductDto> getProducts(Pageable pageable);

    ProductDto getProductById(int id);

    ProductDto createProduct(@Valid CreateProductDto productDto);

    ProductDto updateProduct(@Valid UpdateProductDto productDto);

    void deleteProduct(int id);
}
