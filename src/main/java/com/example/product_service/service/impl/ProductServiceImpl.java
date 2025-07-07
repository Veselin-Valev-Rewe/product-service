package com.example.product_service.service.impl;

import com.example.product_service.data.repository.ProductRepository;
import com.example.product_service.dto.CreateProductDto;
import com.example.product_service.dto.UpdateProductDto;
import com.example.product_service.dto.ProductDto;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Override
    public List<ProductDto> getProducts(Pageable pageable) {
        var products = productRepository.findAll(pageable).getContent();
        return products
                .stream()
                .map(mapper::toProductDto)
                .toList();
    }

    @Override
    public ProductDto getProductById(int id) {
        var product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new EntityNotFoundException(String.format("Product with id %d not found", id));
        }

        return mapper.toProductDto(product.get());
    }

    @Override
    public ProductDto createProduct(CreateProductDto productDto) {
        var product = mapper.toProduct(productDto);
        return mapper.toProductDto(productRepository.save(product));
    }

    @Override
    public ProductDto updateProduct(UpdateProductDto productDto) {
        var product = productRepository.findById(productDto.getId());

        if (product.isEmpty()) {
            throw new EntityNotFoundException(String.format("Product with id %d not found", productDto.getId()));
        }

        var existingProduct = product.get();
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setTitle(productDto.getTitle());
        existingProduct.setImageUrl(productDto.getImageUrl());
        existingProduct.setUniqueIdentifierGtin(productDto.getUniqueIdentifierGtin());
        existingProduct.setUniqueIdentifierNan(productDto.getUniqueIdentifierNan());

        var savedCustomer = productRepository.save(existingProduct);
        return mapper.toProductDto(savedCustomer);
    }

    @Override
    public void deleteProduct(int id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found");
        }

        productRepository.deleteById(id);
    }
}
