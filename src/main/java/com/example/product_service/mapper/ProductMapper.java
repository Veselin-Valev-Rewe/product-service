package com.example.product_service.mapper;

import com.example.product_service.data.entity.Product;
import com.example.product_service.dto.CreateProductDto;
import com.example.product_service.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    ProductDto toProductDto(Product product);
    Product toProduct(CreateProductDto productDto);
}