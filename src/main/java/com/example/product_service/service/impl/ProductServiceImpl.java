package com.example.product_service.service.impl;

import com.example.product_service.data.entity.Product;
import com.example.product_service.data.repository.ProductRepository;
import com.example.product_service.dto.CreateProductDto;
import com.example.product_service.dto.ProductDto;
import com.example.product_service.dto.UpdateProductDto;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.service.ProductService;
import com.example.product_service.util.errormessage.ErrorMessages;
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
        return mapper.toProductDto(getProduct(id));
    }

    @Override
    public ProductDto createProduct(CreateProductDto productDto) {
        var product = mapper.toProduct(productDto);
        return mapper.toProductDto(productRepository.save(product));
    }

    @Override
    public ProductDto updateProduct(int id, UpdateProductDto productDto) {
        var product = getProduct(id);
        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setImageUrl(productDto.getImageUrl());
        product.setUniqueIdentifierGtin(productDto.getUniqueIdentifierGtin());
        product.setUniqueIdentifierNan(productDto.getUniqueIdentifierNan());

        return mapper.toProductDto(productRepository.save(product));
    }

    @Override
    public void deleteProduct(int id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, id));
        }

        productRepository.deleteById(id);
    }

    private Product getProduct(int id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, id)));
    }
}
