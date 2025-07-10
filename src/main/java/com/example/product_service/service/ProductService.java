package com.example.product_service.service;

import com.example.product_service.dto.CreateProductDto;
import com.example.product_service.dto.ProductDto;
import com.example.product_service.dto.UpdateProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductDto> getProducts(Pageable pageable);

    ProductDto getProductById(int id);

    ProductDto createProduct(CreateProductDto productDto);

    ProductDto updateProduct(int id, UpdateProductDto productDto);

    void deleteProduct(int id);
}
