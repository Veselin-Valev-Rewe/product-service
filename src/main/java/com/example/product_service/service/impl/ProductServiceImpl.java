package com.example.product_service.service.impl;

import com.example.product_service.data.entity.Product;
import com.example.product_service.data.repository.ProductRepository;
import com.example.product_service.dto.CreateProductDto;
import com.example.product_service.dto.ProductDto;
import com.example.product_service.dto.UpdateProductDto;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.message.Message;
import com.example.product_service.message.enums.ActionType;
import com.example.product_service.service.ProductMessageProducer;
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
    private final ProductMessageProducer productMessageProducer;

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
    public ProductDto createProduct(CreateProductDto createProductDto) {
        var product = mapper.toProduct(createProductDto);
        var savedProduct = productRepository.save(product);
        var productDto = mapper.toProductDto(savedProduct);
        var payload = new Message<>(ActionType.CREATE, productDto);

        productMessageProducer.sentMessage(String.valueOf(savedProduct.getId()), payload);

        return productDto;
    }

    @Override
    public ProductDto updateProduct(int id, UpdateProductDto updateProductDto) {
        var product = getProduct(id);
        mapper.updateProductFromDto(updateProductDto, product);
        var savedProduct = productRepository.save(product);
        var productDto = mapper.toProductDto(savedProduct);
        var payload = new Message<>(ActionType.UPDATE, productDto);

        productMessageProducer.sentMessage(String.valueOf(savedProduct.getId()), payload);

        return productDto;
    }

    @Override
    public void deleteProduct(int id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, id));
        }

        productRepository.deleteById(id);
        var payload = new Message<>(ActionType.DELETE, (ProductDto) null);

        productMessageProducer.sentMessage(String.valueOf(id), payload);
    }

    private Product getProduct(int id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, id)));
    }
}
